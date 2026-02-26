import { describe, it, expect } from 'vitest';
import { ProductionOptimizationService } from '../service/ProductionOptimizationService';

// Mock data
const products = [
  {
    id: 1,
    name: 'Product A',
    price: 100,
    ingredients: [
      { rawMaterialId: 1, requiredQuantity: 10 },
      { rawMaterialId: 2, requiredQuantity: 5 }
    ]
  },
  {
    id: 2,
    name: 'Product B',
    price: 150,
    ingredients: [
      { rawMaterialId: 1, requiredQuantity: 20 }
    ]
  }
];
const stock = {
  1: 100, // rawMaterialId 1
  2: 50  // rawMaterialId 2
};

describe('ProductionOptimizationService', () => {
  it('should suggest production maximizing total revenue', () => {
    const service = new ProductionOptimizationService();
    const suggestion = service.suggestProduction(products, stock);
    expect(suggestion.totalExpectedRevenue).toBeGreaterThan(0);
    expect(suggestion.suggestedProducts.length).toBeGreaterThan(0);
    // Should prioritize Product B if it gives more revenue
    expect(suggestion.suggestedProducts[0].productName).toBe('Product B');
  });

  it('should handle conflicts for raw materials', () => {
    const service = new ProductionOptimizationService();
    const limitedStock = { 1: 20, 2: 50 };
    const suggestion = service.suggestProduction(products, limitedStock);
    // Only Product B can be produced
    expect(suggestion.suggestedProducts.length).toBe(1);
    expect(suggestion.suggestedProducts[0].productName).toBe('Product B');
  });

  it('should return zero if no stock', () => {
    const service = new ProductionOptimizationService();
    const noStock = { 1: 0, 2: 0 };
    const suggestion = service.suggestProduction(products, noStock);
    expect(suggestion.totalExpectedRevenue).toBe(0);
    expect(suggestion.suggestedProducts.length).toBe(0);
  });
});
