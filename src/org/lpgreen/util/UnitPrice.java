package org.lpgreen.util;

/**
 * It is the UnitPrice interface used for converting a given price with a certain unit and quantity 
 * into another unit and calculate the corresponding quantity and price under the new unit
 * 
 * Creation date: Mar. 17, 2012
 * Last modify date: Mar. 21, 2012
 * 
 * @author  Yan Linda Guo
 * @version 1.0
 */
public interface UnitPrice extends Comparable<UnitPrice>{
	public String getUnit();         // the measuring unit 
	public double getQuantity();     // the quantity in the measuring unit
	public double getPrice();        // the total price
	public String getCurrency();     // the currency used for the total price
	public String getNewUnit();      // the new measuring unit
	public void   setNewUnit(String newUnit);
	public double getNewQuantity();  // the equivalent quantity in the new measuring unit
	public void   setNewQuantity(double newQuantity);
	public double getNewPrice();	// the equivalent price in the new currency
	public void   setNewPrice(double newPrice);
	public String getNewCurrency(); // the new currency
	public void   setNewCurrency(String newCurrenty);
	public double getNewUnitPrice(); // the unit price in the new unit and the new currency
	public void   setNewUnitPrice(double newUnitPrice);
}
