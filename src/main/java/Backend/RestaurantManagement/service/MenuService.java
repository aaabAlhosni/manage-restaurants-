package Backend.RestaurantManagement.service;

import Backend.RestaurantManagement.dto.MenuItemResponseDto;
import Backend.RestaurantManagement.dto.MenuRequestDto;
import Backend.RestaurantManagement.dto.MenuResponseDto;
import Backend.RestaurantManagement.exception.ForbiddenException;
import Backend.RestaurantManagement.exception.ResourceNotFoundException;
import Backend.RestaurantManagement.model.Menu;
import Backend.RestaurantManagement.model.User;
import Backend.RestaurantManagement.repository.MenuRepository;
import Backend.RestaurantManagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MenuService {

    private final MenuRepository menuRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<MenuResponseDto> getMenusForUser(String email) {
        return menuRepository.findByUserEmailOrderByMenuIdAsc(email)
                .stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    public MenuResponseDto createMenu(String email, MenuRequestDto dto) {
        User user = userRepository.findById(email)
                .orElseThrow(() -> new ResourceNotFoundException("User account not found."));

        Menu menu = Menu.builder()
                .title(dto.getTitle())
                .category(dto.getCategory())
                .user(user)
                .build();

        return toResponseDto(menuRepository.save(menu));
    }

    @Transactional(readOnly = true)
    public MenuResponseDto getMenuById(Long id, String email) {
        return toResponseDto(findAndVerifyOwnership(id, email));
    }

    public MenuResponseDto updateMenu(Long id, String email, MenuRequestDto dto) {
        Menu menu = findAndVerifyOwnership(id, email);
        menu.setTitle(dto.getTitle());
        menu.setCategory(dto.getCategory());
        return toResponseDto(menuRepository.save(menu));
    }

    public void deleteMenu(Long id, String email) {
        menuRepository.delete(findAndVerifyOwnership(id, email));
    }

    @Transactional(readOnly = true)
    public MenuResponseDto getPublicMenu(Long id) {
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("This menu does not exist."));
        return toResponseDto(menu);
    }

    private Menu findAndVerifyOwnership(Long id, String email) {
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("This menu does not exist."));
        if (!menu.getUser().getEmail().equals(email)) {
            throw new ForbiddenException("You cannot edit this menu.");
        }
        return menu;
    }

    private MenuResponseDto toResponseDto(Menu menu) {
        List<MenuItemResponseDto> items = menu.getItems() == null ? List.of() :
                menu.getItems().stream()
                        .map(item -> MenuItemResponseDto.builder()
                                .menuItemId(item.getMenuItemId())
                                .name(item.getName())
                                .price(item.getPrice())
                                .description(item.getDescription())
                                .available(item.isAvailable())
                                .build())
                        .collect(Collectors.toList());

        return MenuResponseDto.builder()
                .menuId(menu.getMenuId())
                .title(menu.getTitle())
                .category(menu.getCategory())
                .items(items)
                .build();
    }
}
