package Backend.RestaurantManagement.controller;

import Backend.RestaurantManagement.dto.MenuRequestDto;
import Backend.RestaurantManagement.dto.MenuResponseDto;
import Backend.RestaurantManagement.service.MenuService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menus")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @GetMapping
    public ResponseEntity<List<MenuResponseDto>> getAllMenus(
            @AuthenticationPrincipal OAuth2User principal) {
        return ResponseEntity.ok(menuService.getMenusForUser(principal.getAttribute("email")));
    }

    @PostMapping
    public ResponseEntity<MenuResponseDto> createMenu(
            @AuthenticationPrincipal OAuth2User principal,
            @Valid @RequestBody MenuRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(menuService.createMenu(principal.getAttribute("email"), dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MenuResponseDto> getMenuById(
            @AuthenticationPrincipal OAuth2User principal,
            @PathVariable Long id) {
        return ResponseEntity.ok(menuService.getMenuById(id, principal.getAttribute("email")));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MenuResponseDto> updateMenu(
            @AuthenticationPrincipal OAuth2User principal,
            @PathVariable Long id,
            @Valid @RequestBody MenuRequestDto dto) {
        return ResponseEntity.ok(menuService.updateMenu(id, principal.getAttribute("email"), dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMenu(
            @AuthenticationPrincipal OAuth2User principal,
            @PathVariable Long id) {
        menuService.deleteMenu(id, principal.getAttribute("email"));
        return ResponseEntity.noContent().build();
    }
}
