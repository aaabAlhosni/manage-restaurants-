package Backend.RestaurantManagement.service;

import Backend.RestaurantManagement.dto.MenuItemRequestDto;
import Backend.RestaurantManagement.dto.MenuItemResponseDto;
import Backend.RestaurantManagement.exception.ForbiddenException;
import Backend.RestaurantManagement.exception.ResourceNotFoundException;
import Backend.RestaurantManagement.model.Menu;
import Backend.RestaurantManagement.model.MenuItem;
import Backend.RestaurantManagement.repository.MenuItemRepository;
import Backend.RestaurantManagement.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MenuItemService {

    private final MenuRepository menuRepository;
    private final MenuItemRepository menuItemRepository;

    public MenuItemResponseDto addItem(Long menuId, String email, MenuItemRequestDto dto) {
        Menu menu = findAndVerifyOwnership(menuId, email);

        MenuItem item = MenuItem.builder()
                .name(dto.getName())
                .price(dto.getPrice())
                .description(dto.getDescription())
                .available(dto.getAvailable())
                .menu(menu)
                .build();

        return toResponseDto(menuItemRepository.save(item));
    }

    public MenuItemResponseDto updateItem(Long menuId, Long itemId, String email, MenuItemRequestDto dto) {
        findAndVerifyOwnership(menuId, email);

        MenuItem item = menuItemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("This item does not exist."));

        item.setName(dto.getName());
        item.setPrice(dto.getPrice());
        item.setDescription(dto.getDescription());
        item.setAvailable(dto.getAvailable());

        return toResponseDto(menuItemRepository.save(item));
    }

    public void deleteItem(Long menuId, Long itemId, String email) {
        findAndVerifyOwnership(menuId, email);

        MenuItem item = menuItemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("This item does not exist."));

        menuItemRepository.delete(item);
    }

    private Menu findAndVerifyOwnership(Long menuId, String email) {
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new ResourceNotFoundException("This menu does not exist."));
        if (!menu.getUser().getEmail().equals(email)) {
            throw new ForbiddenException("You cannot edit this menu.");
        }
        return menu;
    }

    private MenuItemResponseDto toResponseDto(MenuItem item) {
        return MenuItemResponseDto.builder()
                .menuItemId(item.getMenuItemId())
                .name(item.getName())
                .price(item.getPrice())
                .description(item.getDescription())
                .available(item.isAvailable())
                .build();
    }
}
