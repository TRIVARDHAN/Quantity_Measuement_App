public class Quantity_Measuement_App {

    // ===================== ENUM =====================
    enum LengthUnit {
        FEET(12.0),
        INCH(1.0),
        YARD(36.0),
        CENTIMETER(0.393701);

        private final double inInches;

        LengthUnit(double inInches) {
            this.inInches = inInches;
        }

        public double getFactor() {
            return inInches;
        }
    }

    // ===================== VALUE CLASS =====================
    static class QuantityLength {
        private final double value;
        private final LengthUnit unit;

        public QuantityLength(double value, LengthUnit unit) {
            if (!Double.isFinite(value)) {
                throw new IllegalArgumentException("Invalid value");
            }
            if (unit == null) {
                throw new IllegalArgumentException("Unit cannot be null");
            }
            this.value = value;
            this.unit = unit;
        }

        // Convert to base unit (inches)
        private double toBase() {
            return value * unit.getFactor();
        }

        // ===================== UC1 - UC4 EQUALITY =====================
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            QuantityLength that = (QuantityLength) obj;
            return Math.abs(this.toBase() - that.toBase()) < 0.0001;
        }

        // ===================== UC5 - CONVERSION =====================
        public double convertTo(LengthUnit target) {
            if (target == null) {
                throw new IllegalArgumentException("Target unit cannot be null");
            }
            double base = toBase();
            return base / target.getFactor();
        }

        // ===================== UC6 - ADDITION (DEFAULT UNIT) =====================
        public QuantityLength add(QuantityLength other) {
            return add(other, this.unit);
        }

        // ===================== UC7 - ADDITION (EXPLICIT UNIT) =====================
        public QuantityLength add(QuantityLength other, LengthUnit target) {
            if (other == null || target == null) {
                throw new IllegalArgumentException("Null not allowed");
            }

            double sumBase = this.toBase() + other.toBase();
            double resultValue = sumBase / target.getFactor();

            return new QuantityLength(resultValue, target);
        }

        @Override
        public String toString() {
            return "Quantity(" + value + ", " + unit + ")";
        }
    }

    // ===================== DEMO MAIN =====================
    public static void main(String[] args) {

        // UC1 - Equality
        System.out.println("UC1 Equality:");
        System.out.println(new QuantityLength(1.0, LengthUnit.FEET)
                .equals(new QuantityLength(1.0, LengthUnit.FEET)));

        // UC5 - Conversion
        System.out.println("\nUC5 Conversion:");
        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
        System.out.println(q1.convertTo(LengthUnit.INCH));

        // UC6 - Addition default unit
        System.out.println("\nUC6 Addition:");
        QuantityLength a = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength b = new QuantityLength(12.0, LengthUnit.INCH);
        System.out.println(a.add(b));

        // UC7 - Addition explicit unit
        System.out.println("\nUC7 Addition (Target Unit):");
        System.out.println(a.add(b, LengthUnit.YARD));
        System.out.println(a.add(b, LengthUnit.FEET));
        System.out.println(a.add(b, LengthUnit.INCH));
    }
}