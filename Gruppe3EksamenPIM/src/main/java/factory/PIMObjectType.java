package factory;

public enum PIMObjectType {

    ATTRIBUTE {
        @Override
        public String toString() {
            return attribute;
        }
    },
    BUNDLE {
        @Override
        public String toString() {
            return bundle;
        }
    },
    CATEGORY {
        @Override
        public String toString() {
            return category;
        }
    },
    DISTRIBUTOR {
        @Override
        public String toString() {
            return distributor;
        }
    },
    PRODUCT {
        @Override
        public String toString() {
            return product;
        }
    };

    private final static String attribute = "Attribute";
    private final static String bundle = "Bundle";
    private final static String category = "Category";
    private final static String distributor = "Distributor";
    private final static String product = "Product";

    public static PIMObjectType getPIMObjectType(String pimObjectString) {
        switch (pimObjectString) {
            case attribute:
                return PIMObjectType.ATTRIBUTE;
            case bundle:
                return PIMObjectType.BUNDLE;
            case category:
                return PIMObjectType.CATEGORY;
            case distributor:
                return PIMObjectType.DISTRIBUTOR;
            case product:
                return PIMObjectType.PRODUCT;
            default:
                throw new IllegalArgumentException("'" + pimObjectString + "' does not match any known PIM object type.");
        }
    }
}
