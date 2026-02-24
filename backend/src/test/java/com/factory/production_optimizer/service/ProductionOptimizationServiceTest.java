package com.factory.production_optimizer.service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.factory.production_optimizer.dto.ProductionSuggestionDTO;
import com.factory.production_optimizer.model.Product;
import com.factory.production_optimizer.model.ProductIngredient;
import com.factory.production_optimizer.model.RawMaterial;
import com.factory.production_optimizer.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
public class ProductionOptimizationServiceTest {
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductionOptimizationService service;

    @Test
    void estoqueSuficienteParaTodosProdutos() {
        RawMaterial cafe = new RawMaterial(1L, "CAFE", "Café", 1000.0);
        RawMaterial leite = new RawMaterial(2L, "LEITE", "Leite", 1000.0);
        ProductIngredient pi1 = new ProductIngredient(1L, null, cafe, 100.0);
        ProductIngredient pi2 = new ProductIngredient(2L, null, leite, 200.0);
        Product produto1 = new Product(1L, "P1", "Café com Leite", BigDecimal.valueOf(10), Arrays.asList(pi1, pi2));
        pi1.setProduct(produto1);
        pi2.setProduct(produto1);
        when(productRepository.findAll()).thenReturn(List.of(produto1));
        ProductionSuggestionDTO dto = service.suggestProduction();
        assertEquals(1, dto.getSuggestedProducts().size());
        assertEquals("Café com Leite", dto.getSuggestedProducts().get(0).getProductName());
        assertEquals(5, dto.getSuggestedProducts().get(0).getQuantityToProduce());
    }

    @Test
    void conflitoDeMateriaPrimaEntreProdutos() {
        RawMaterial cafe = new RawMaterial(1L, "CAFE", "Café", 100.0);
        ProductIngredient pi1 = new ProductIngredient(1L, null, cafe, 100.0);
        Product produto1 = new Product(1L, "P1", "Café Expresso", BigDecimal.valueOf(20), List.of(pi1));
        ProductIngredient pi2 = new ProductIngredient(2L, null, cafe, 50.0);
        Product produto2 = new Product(2L, "P2", "Café Duplo", BigDecimal.valueOf(10), List.of(pi2));
        pi1.setProduct(produto1);
        pi2.setProduct(produto2);
        when(productRepository.findAll()).thenReturn(List.of(produto1, produto2));
        ProductionSuggestionDTO dto = service.suggestProduction();
        assertEquals(1, dto.getSuggestedProducts().size());
        assertEquals("Café Expresso", dto.getSuggestedProducts().get(0).getProductName());
        assertEquals(1, dto.getSuggestedProducts().get(0).getQuantityToProduce());
    }

    @Test
    void estoqueZeradoRetornaVazio() {
        RawMaterial cafe = new RawMaterial(1L, "CAFE", "Café", 0.0);
        ProductIngredient pi1 = new ProductIngredient(1L, null, cafe, 100.0);
        Product produto1 = new Product(1L, "P1", "Café Expresso", BigDecimal.valueOf(20), List.of(pi1));
        pi1.setProduct(produto1);
        when(productRepository.findAll()).thenReturn(List.of(produto1));
        ProductionSuggestionDTO dto = service.suggestProduction();
        assertTrue(dto.getSuggestedProducts().isEmpty());
    }

    @Test
    void produtoComIngredienteSemEstoqueNaoSugerido() {
        RawMaterial cafe = new RawMaterial(1L, "CAFE", "Café", 100.0);
        RawMaterial leite = new RawMaterial(2L, "LEITE", "Leite", 0.0);
        ProductIngredient pi1 = new ProductIngredient(1L, null, cafe, 100.0);
        ProductIngredient pi2 = new ProductIngredient(2L, null, leite, 50.0);
        Product produto1 = new Product(1L, "P1", "Café com Leite", BigDecimal.valueOf(10), Arrays.asList(pi1, pi2));
        pi1.setProduct(produto1);
        pi2.setProduct(produto1);
        when(productRepository.findAll()).thenReturn(List.of(produto1));
        ProductionSuggestionDTO dto = service.suggestProduction();
        assertTrue(dto.getSuggestedProducts().isEmpty());
    }
}
