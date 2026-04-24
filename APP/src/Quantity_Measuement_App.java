public class Quantity_Measuement_App {

    // ================= ENUM =================
    enum LengthUnit {

        FEET(1.0),
        INCHES(1.0 / 12.0),
        YARDS(3.0),
        CENTIMETERS(0.393701 / 12.0);

        private final double conversionFactorToFeet;

        LengthUnit(double conversionFactorToFeet) {
            this.conversionFactorToFeet = conversionFactorToFeet;
        }

        public double toFeet(double value) {
            return value * conversionFactorToFeet;
        }

        public double fromFeet(double valueInFeet) {
            return valueInFeet / conversionFactorToFeet;
        }
    }

    // ================= CORE CLASS =================
    static class QuantityLength {

        private final double value;
        private final LengthUnit unit;

        public QuantityLength(double value, LengthUnit unit) {
            if (unit == null || Double.isNaN(value) || Double.isInfinite(value)) {
                throw new IllegalArgumentException("Invalid value or unit");
            }
            this.value = value;
            this.unit = unit;
        }

        private double toFeet() {
            return unit.toFeet(value);
        }

        @Override
        public boolean equals(Object obj) {

            if (this == obj) return true;
            if (!(obj instanceof QuantityLength)) return false;

            QuantityLength other = (QuantityLength) obj;

            double epsilon = 0.0001;
            return Math.abs(this.toFeet() - other.toFeet()) < epsilon;
        }

        @Override
        public String toString() {
            return value + " " + unit;
        }
    }

    // ================= UC5 CONVERSION API =================
    public static double convert(double value, LengthUnit from, LengthUnit to) {

        if (from == null || to == null ||
                Double.isNaN(value) || Double.isInfinite(value)) {
            throw new IllegalArgumentException("Invalid input");
        }

        double valueInFeet = from.toFeet(value);
        return to.fromFeet(valueInFeet);
    }

    // ================= MAIN METHOD =================
    public static void main(String[] args) {

        System.out.println("1 Feet → Inches = " +
                convert(1.0, LengthUnit.FEET, LengthUnit.INCHES));

        System.out.println("3 Yards → Feet = " +
                convert(3.0, LengthUnit.YARDS, LengthUnit.FEET));

        System.out.println("36 Inches → Yards = " +
                convert(36.0, LengthUnit.INCHES, LengthUnit.YARDS));

        System.out.println("1 CM → Inches = " +
                convert(1.0, LengthUnit.CENTIMETERS, LengthUnit.INCHES));

        System.out.println("0 Feet → Inches = " +
                convert(0.0, LengthUnit.FEET, LengthUnit.INCHES));

        // Equality checks (optional demo)
        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(12.0, LengthUnit.INCHES);

        System.out.println("1 Feet == 12 Inches ? " + q1.equals(q2));
    }
}