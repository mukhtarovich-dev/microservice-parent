package mukhtarovich.uz.inventory_service.repository;

import mukhtarovich.uz.inventory_service.module.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
@CrossOrigin
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    List<Inventory>  findBySkuCodeIn(List<String> skuCodes);
}
