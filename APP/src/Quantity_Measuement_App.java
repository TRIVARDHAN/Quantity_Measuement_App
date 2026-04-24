public class Quantity_Measuement_App {

    // ================= ENUM =================
    enum LengthUnit {

        FEET(1.0),
        INCHES(1.0 / 12.0),
        YARDS(3.0),
        CENTIMETERS(0.393701 / 12.0);

        private final double factorToFeet;

        LengthUnit(double factorToFeet) {
            this.factorToFeet = factorToFeet;
        }

        public double toFeet(double value) {
            return value * factorToFeet;
        }

        public double fromFeet(double valueInFeet) {
            return valueInFeet / factorToFeet;
        }
    }

    // ================= VALUE OBJECT =================
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

        // ================= EQUALITY =================
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof QuantityLength)) return false;

            QuantityLength other = (QuantityLength) obj;

            double epsilon = 0.0001;
            return Math.abs(this.toFeet() - other.toFeet()) < epsilon;
        }

        // ================= UC6 ADDITION =================
        public QuantityLength add(QuantityLength other, LengthUnit resultUnit) {

            if (other == null || resultUnit == null) {
                throw new IllegalArgumentException("Null values not allowed");
            }

            double sumInFeet = this.toFeet() + other.toFeet();

            double resultValue = resultUnit.fromFeet(sumInFeet);

            return new QuantityLength(resultValue, resultUnit);
        }

        @Override
        public String toString() {
            return value + " " + unit;
        }
    }

    // ================= MAIN METHOD =================
    public static void main(String[] args) {

        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(12.0, LengthUnit.INCHES);

        QuantityLength result1 = q1.add(q2, LengthUnit.FEET);
        System.out.println("1 Feet + 12 Inches = " + result1);

        QuantityLength q3 = new QuantityLength(1.0, LengthUnit.YARDS);
        QuantityLength q4 = new QuantityLength(3.0, LengthUnit.FEET);

        QuantityLength result2 = q3.add(q4, LengthUnit.YARDS);
        System.out.println("1 Yard + 3 Feet = " + result2);

        QuantityLength q5 = new QuantityLength(36.0, LengthUnit.INCHES);
        QuantityLength result3 = q5.add(q3, LengthUnit.INCHES);
        System.out.println("36 Inches + 1 Yard = " + result3);

        QuantityLength q6 = new QuantityLength(5.0, LengthUnit.FEET);
        QuantityLength q7 = new QuantityLength(-2.0, LengthUnit.FEET);

        QuantityLength result4 = q6.add(q7, LengthUnit.FEET);
        System.out.println("5 Feet + (-2 Feet) = " + result4);
    }
}