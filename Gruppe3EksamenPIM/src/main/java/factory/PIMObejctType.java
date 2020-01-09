package factory;

public enum PIMObejctType {

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

    public static PIMObejctType getPIMObjectType(String pimObjectString) {
        switch (pimObjectString) {
            case attribute:
                return PIMObejctType.ATTRIBUTE;
            case bundle:
                return PIMObejctType.BUNDLE;
            case category:
                return PIMObejctType.CATEGORY;
            case distributor:
                return PIMObejctType.DISTRIBUTOR;
            case product:
                return PIMObejctType.PRODUCT;
            default:
                throw new IllegalArgumentException("'" + pimObjectString + "' does not match any known PIM object type.");
        }
    }
}
