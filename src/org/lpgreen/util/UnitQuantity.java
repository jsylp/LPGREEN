package org.lpgreen.util;

/**
 * It is the UnitPrice interface used for converting a given price with a certain unit and quantity 
 * into another unit and calculate the corresponding quantity and price under the new unit
 * 
 * Creation date: Mar. 28, 2012
 * Last modify date: Mar. 28, 2012
 * 
 * @author  Jiaxun Stephen Yu
 * @version 1.0
 */
public interface UnitQuantity {
	public String getUnit();         // the measuring unit 
	public double getQuantity();     // the quantity in the measuring unit
	public String getNewUnit();      // the new measuring unit
	public void   setNewUnit(String newUnit);
	public double getNewQuantity();  // the equivalent quantity in the new measuring unit
	public void   setNewQuantity(double newQuantity);
}
