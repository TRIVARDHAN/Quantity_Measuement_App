public class Quantity_Measuement_App {

    // ================= Length Unit Enum =================
    enum LengthUnit {

        FEET(1.0),
        INCH(1.0 / 12.0),
        YARD(3.0),                // 1 yard = 3 feet
        CENTIMETER(0.0328084);    // 1 cm = 0.0328084 feet (derived)

        private final double conversionFactorToFeet;

        LengthUnit(double conversionFactorToFeet) {
            this.conversionFactorToFeet = conversionFactorToFeet;
        }

        public double toFeet(double value) {
            return value * conversionFactorToFeet;
        }
    }

    // ================= Quantity Class =================
    static class QuantityLength {
        private final double value;
        private final LengthUnit unit;

        public QuantityLength(double value, LengthUnit unit) {
            this.value = value;
            this.unit = unit;
        }

        private double getValueInFeet() {
            return unit.toFeet(value);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            QuantityLength other = (QuantityLength) obj;

            return Double.compare(this.getValueInFeet(), other.getValueInFeet()) == 0;
        }
    }

    // ================= Demo =================
    public static void main(String[] args) {

        QuantityLength yard = new QuantityLength(1.0, LengthUnit.YARD);
        QuantityLength feet = new QuantityLength(3.0, LengthUnit.FEET);
        QuantityLength inches = new QuantityLength(36.0, LengthUnit.INCH);
        QuantityLength cm = new QuantityLength(1.0, LengthUnit.CENTIMETER);

        System.out.println("1 yard vs 3 feet: " + yard.equals(feet));
        System.out.println("1 yard vs 36 inches: " + yard.equals(inches));
        System.out.println("2 yards vs 2 yards: " +
                new QuantityLength(2.0, LengthUnit.YARD)
                        .equals(new QuantityLength(2.0, LengthUnit.YARD)));

        System.out.println("1 cm vs 0.393701 inch: " +
                cm.equals(new QuantityLength(0.393701, LengthUnit.INCH)));
    }
}