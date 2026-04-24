public class Quantity_Measuement_App {

    // ===================== STANDALONE ENUM (UC8) =====================
    enum LengthUnit {

        FEET(12.0),
        INCHES(1.0),
        YARDS(36.0),
        CENTIMETERS(0.393701);

        private final double toInches;

        LengthUnit(double toInches) {
            this.toInches = toInches;
        }

        // Convert value in THIS unit → base unit (INCHES)
        public double convertToBaseUnit(double value) {
            return value * toInches;
        }

        // Convert value from base unit (INCHES) → THIS unit
        public double convertFromBaseUnit(double baseValue) {
            return baseValue / toInches;
        }
    }

    // ===================== QUANTITY CLASS (REFRACTORED UC8) =====================
    static class QuantityLength {

        private final double value;
        private final LengthUnit unit;

        public QuantityLength(double value, LengthUnit unit) {
            if (unit == null) throw new IllegalArgumentException("Unit cannot be null");
            if (!Double.isFinite(value)) throw new IllegalArgumentException("Invalid value");

            this.value = value;
            this.unit = unit;
        }

        // --------------------- INTERNAL BASE CONVERSION ---------------------
        private double toBase() {
            return unit.convertToBaseUnit(value);
        }

        // ===================== UC1–UC4 EQUALITY =====================
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof QuantityLength)) return false;

            QuantityLength other = (QuantityLength) obj;

            return Math.abs(this.toBase() - other.toBase()) < 0.0001;
        }

        // ===================== UC5 CONVERSION =====================
        public double convertTo(LengthUnit target) {
            if (target == null) throw new IllegalArgumentException("Target unit cannot be null");

            double base = this.toBase();
            return target.convertFromBaseUnit(base);
        }

        // ===================== UC6 ADDITION (DEFAULT UNIT) =====================
        public QuantityLength add(QuantityLength other) {
            return add(other, this.unit);
        }

        // ===================== UC7 ADDITION (EXPLICIT TARGET UNIT) =====================
        public QuantityLength add(QuantityLength other, LengthUnit target) {
            if (other == null || target == null)
                throw new IllegalArgumentException("Null not allowed");

            double sumBase =
                    this.toBase() + other.toBase();

            double result = target.convertFromBaseUnit(sumBase);

            return new QuantityLength(result, target);
        }

        @Override
        public String toString() {
            return "Quantity(" + value + ", " + unit + ")";
        }
    }

    // ===================== DEMO MAIN =====================
    public static void main(String[] args) {

        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(12.0, LengthUnit.INCHES);

        System.out.println("=== UC8 Refactored Output ===");

        // Equality
        System.out.println("Equality:");
        System.out.println(q1.equals(q2)); // true

        // Conversion
        System.out.println("\nConversion:");
        System.out.println(q1.convertTo(LengthUnit.INCHES)); // 12.0

        // UC6 Addition
        System.out.println("\nAddition (default unit):");
        System.out.println(q1.add(q2)); // 2 FEET

        // UC7 Addition (explicit unit)
        System.out.println("\nAddition (target unit):");
        System.out.println(q1.add(q2, LengthUnit.YARDS)); // ~0.667 yards
        System.out.println(q1.add(q2, LengthUnit.INCHES)); // 24 inches

        // Direct enum usage (UC8 highlight)
        System.out.println("\nEnum direct conversion:");
        System.out.println(LengthUnit.INCHES.convertToBaseUnit(12.0)); // 12 inches → 1 foot
        System.out.println(LengthUnit.FEET.convertFromBaseUnit(1.0));   // 1 foot → 1 foot
    }
}