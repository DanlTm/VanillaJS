package com.inventory.productsapi;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller for the page that consumes the external REST API.
 */
@Controller
public class ProductsApiController {

    @GetMapping("/products-api")
    public String productsFromApi() {
        return "products-api/list";
    }
}
