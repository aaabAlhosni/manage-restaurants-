package Backend.RestaurantManagement.controller;

import Backend.RestaurantManagement.dto.MenuResponseDto;
import Backend.RestaurantManagement.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/menu")
@RequiredArgsConstructor
public class PublicMenuController {

    private final MenuService menuService;

    @GetMapping("/{id}")
    public ResponseEntity<MenuResponseDto> getPublicMenu(@PathVariable Long id) {
        return ResponseEntity.ok(menuService.getPublicMenu(id));
    }
}
