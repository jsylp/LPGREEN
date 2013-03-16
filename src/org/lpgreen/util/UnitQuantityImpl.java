package org.lpgreen.util;

// Define a packaged product
//
public class UnitQuantityImpl implements UnitQuantity {

	// The measuring unit used for the product
	//
	private String unit;

	// The quantity in the measuring unit
	//
	private double quantity;

	// The new measuring unit
	//
	private String unitNew;

	// The equivalent quantity in the new measuring unit
	//
	private double quantityNew;

	// Constructor
	//
	public UnitQuantityImpl(String unit, double quantity) {
		this.unit            = unit;
		this.quantity        = quantity;
		this.unitNew         = null;
		this.quantityNew     = 0d;
	}

	// methods for the UnitQuantity interface
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

	// Output the object in string format
	//
	public String toString() {
		String s = "" + quantity;
		if (unit != null && !unit.isEmpty()) {
			s += " " + unit;
		}
		s += " => " + quantityNew;
		if (unitNew != null && !unitNew.isEmpty()) {
			s += " " + unitNew;
		}
		return s;
    }
}
