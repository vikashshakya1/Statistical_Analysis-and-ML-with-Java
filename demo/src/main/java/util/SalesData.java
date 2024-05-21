package util;

public class SalesData {
    private String Segment;
    private int UnitsSold;
    private int GrossSales;
    private int Discounts;
    private int Sales;

    public SalesData(String Segment,int UnitsSold,int GrossSales,int Discounts,int Sales){
        this.Segment = Segment;
        this.UnitsSold = UnitsSold;
        this.GrossSales = GrossSales;
        this.Discounts = Discounts;
        this.Sales = Sales;
    }
    public SalesData(int segment2, String country, String product, String discountBand, double unitsSold2, double getGrossSales, double salePrice, double grossSales2, double discounts3, double sales3, double cogs, double profit, String date, int discounts2, String monthName, int sales2, double profit1) {
        //TODO Auto-generated constructor stub
    }
    public String getSegment(){
        return Segment;
    }
    public void setSegment(String Segment){
        this.Segment = Segment;
    }

    public int getUnitsSold(){
        return UnitsSold;
    }
    public void setUnitsSold(int UnitsSold){
        this.UnitsSold  = UnitsSold;
    }
    public int getGrossSales(){
        return GrossSales;
    }
    public void setGrossSales(int GrossSales){
        this.GrossSales = GrossSales;
    }
    public int getDiscounts(){
        return Discounts;
    }
    public void setDiscounts(int Discounts){
        this.Discounts = Discounts;
    }
    public int getSales(){
        return Sales;
    }
    public void setSales(int Sales){
        this.Sales = Sales;
    }
    
}
