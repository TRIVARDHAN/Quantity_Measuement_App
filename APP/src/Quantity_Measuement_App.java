public class Quantity_Measuement_App {

    public static void main(String[] args) {

        System.out.println("=== Quantity Measurement App ===");

        Feet value1 = new Feet(1.0);
        Feet value2 = new Feet(1.0);

        boolean result = value1.equals(value2);

        System.out.println("Input: 1.0 ft and 1.0 ft");
        System.out.println("Output: Equal (" + result + ")");
    }

    // Inner class representing Feet measurement
    static class Feet {
        private final double value;

        public Feet(double value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object obj) {

            // same reference check
            if (this == obj) return true;

            // null check
            if (obj == null) return false;

            // type check
            if (getClass() != obj.getClass()) return false;

            // safe casting
            Feet other = (Feet) obj;

            // value comparison
            return Double.compare(this.value, other.value) == 0;
        }
    }
}