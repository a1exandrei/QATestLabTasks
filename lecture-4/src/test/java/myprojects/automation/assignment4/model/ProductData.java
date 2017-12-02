package myprojects.automation.assignment4.model;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Random;

public class ProductData {
    private String name;
    private int qty;
    private float price;

    public ProductData(String name, int qty, float price) {
        this.name = name;
        this.qty = qty;
        this.price = price;
    }

	public String getName() {
        return name;
    }

    public Integer getQty() {
        return qty;
    }

    public String getPrice() {
    	if (price > 100) price -= 0.1;
        DecimalFormatSymbols separators = new DecimalFormatSymbols();
        separators.setDecimalSeparator(',');
        return new DecimalFormat("#0.00", separators).format(price);
    }

    /**
     * @return New Product object with random name, quantity and price values.
     */
    public static ProductData generate() {
        Random random = new Random();
        return new ProductData(
                "New Product " + System.currentTimeMillis(),
                random.nextInt(99) + 1,
                (float) (random.nextInt(100) + 0.1));
    }
}
