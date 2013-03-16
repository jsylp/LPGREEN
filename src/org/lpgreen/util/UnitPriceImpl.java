package org.lpgreen.util;

// Define a packaged product
//
public class UnitPriceImpl implements UnitPrice, Comparable<UnitPrice> {

	// The measuring unit used for the product
	//
	private String unit;

	// The quantity in the measuring unit
	//
	private double quantity;

	// The total price of the product
	//
	private double price;

	// The currency used for the total price
	//
	private String currency;

	// The new measuring unit
	//
	private String unitNew;

	// The equivalent quantity in the new measuring unit
	//
	private double quantityNew;

	// The equivalent total price in the new currency
	//
	private double priceNew;

	// The new currency used for the new price
	//
	private String currencyNew;

	// The per unit price in the new measuring unit and the new currency
	//
	private double pricePerUnitNew;

	// Constructor
	//
	public UnitPriceImpl(String unit, double quantity, double price, String currency) {
		this.unit            = unit;
		this.quantity        = quantity;
		this.price           = price;
		this.currency        = currency;
		this.unitNew         = null;
		this.quantityNew     = 0d;
		this.priceNew        = 0d;
		this.currencyNew     = null;
		this.pricePerUnitNew = 0d;
	}

	// methods for the UnitPrice interface
	//

	// the measuring unit
	//
	public String getUnit() {
		return unit;
	}

	// the quantity in the measuring unit
	//
	public double getQuantity() {
		return quantity;
	}

	// the total price
	//
	public double getPrice() {
		return price;
	}

	// the currency used for the total price
	//
	public String getCurrency() {
		return currency;
	}

	// the new measuring unit
	//
	public String getNewUnit() {
		return unitNew;
	}
	public void   setNewUnit(String newUnit) {
		this.unitNew = newUnit;
	}

	// the equivalent quantity in the new measuring unit
	//
	public double getNewQuantity() {
		return quantityNew;
	}
	public void   setNewQuantity(double newQuantity) {
		this.quantityNew = newQuantity;
	}

	// the equivalent price in the new currency
	//
	public double getNewPrice() {
		return priceNew;
	}
	public void   setNewPrice(double newPrice) {
		this.priceNew = newPrice;
	}

	// the new currency
	//
	public String getNewCurrency() {
		return currencyNew;
	}
	public void   setNewCurrency(String newCurrency) {
		this.currencyNew = newCurrency;
	}

	// the unit price in the new unit and the new currency
	//
	public double getNewUnitPrice() {
		return pricePerUnitNew;
	}
	public void   setNewUnitPrice(double newUnitPrice) {
		this.pricePerUnitNew = newUnitPrice;
	}

	// Comparable interface
	//
	public int compareTo(UnitPrice price2) {
		return (Math.abs(pricePerUnitNew - price2.getNewUnitPrice()) <= UnitsUtil.unitEpsilon) ? 0 :
			((pricePerUnitNew - price2.getNewUnitPrice()) < 0 ? -1 : 1);
    }

	// Output the object in string format
	//
	public String toString() {
		String s = price + " " + currency + " for " + quantity;
		if (unit != null && !unit.isEmpty()) {
			s += " " + unit;
		}
		s += " => " + priceNew + " " + currencyNew + " for " + quantityNew;
		if (unitNew != null && !unitNew.isEmpty()) {
			s += " " + unitNew + ", " + pricePerUnitNew + " " + currencyNew + "/" + unitNew;
		}
		else {
			s += ", " + pricePerUnitNew + " " + currencyNew + "/unit";
		}
		return s;
    }
}
