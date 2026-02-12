package mukhtarovich.uz.inventory_service.controller;

import lombok.RequiredArgsConstructor;
import mukhtarovich.uz.inventory_service.dto.ApiResponse;
import mukhtarovich.uz.inventory_service.service.InventoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {
        private final InventoryService inventoryService;
    @GetMapping("/{sku-code}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ApiResponse<Boolean>> isInStock(@PathVariable("sku-code") String skuCode) {
        Boolean inStock = inventoryService.isInStock(skuCode);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(true,skuCode+" Omborda  mavjud",inStock));
    }
}
