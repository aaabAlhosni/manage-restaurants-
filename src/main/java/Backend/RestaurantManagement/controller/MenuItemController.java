package Backend.RestaurantManagement.controller;

import Backend.RestaurantManagement.dto.MenuItemRequestDto;
import Backend.RestaurantManagement.dto.MenuItemResponseDto;
import Backend.RestaurantManagement.service.MenuItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/menus/{menuId}/items")
@RequiredArgsConstructor
public class MenuItemController {

    private final MenuItemService menuItemService;

    @PostMapping
    public ResponseEntity<MenuItemResponseDto> addItem(
            @AuthenticationPrincipal OAuth2User principal,
            @PathVariable Long menuId,
            @Valid @RequestBody MenuItemRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(menuItemService.addItem(menuId, principal.getAttribute("email"), dto));
    }

    @PutMapping("/{itemId}")
    public ResponseEntity<MenuItemResponseDto> updateItem(
            @AuthenticationPrincipal OAuth2User principal,
            @PathVariable Long menuId,
            @PathVariable Long itemId,
            @Valid @RequestBody MenuItemRequestDto dto) {
        return ResponseEntity.ok(
                menuItemService.updateItem(menuId, itemId, principal.getAttribute("email"), dto));
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> deleteItem(
            @AuthenticationPrincipal OAuth2User principal,
            @PathVariable Long menuId,
            @PathVariable Long itemId) {
        menuItemService.deleteItem(menuId, itemId, principal.getAttribute("email"));
        return ResponseEntity.noContent().build();
    }
}
