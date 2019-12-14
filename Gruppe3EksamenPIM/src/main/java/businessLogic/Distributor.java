package businessLogic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import persistence.Json.Exclude;

public class Distributor extends PIMObject {

    @Exclude
    private TreeSet<Product> distributorProducts;

    private static TreeSet<Distributor> distributorList = new TreeSet();

    public Distributor(int distributorID, String distributorTitle, String distributorDescription) {
        super(distributorID, distributorTitle, distributorDescription);
        this.distributorProducts = new TreeSet();
    }

    /**
     * Check if user input is valid for businessLogic parameters for the distributor.
     *
     * @param distributorName User input for distributor title.
     * @param distributorDescription User input for distributor description.
     * @return boolean true if user input is valid.
     * @throws IllegalArgumentException if user input is not valid, with message explaining what businesslogic was violated.
     */
    public static boolean validateDistributorInput(String distributorName, String distributorDescription) throws IllegalArgumentException {
        if (distributorName.isEmpty()) {
            throw new IllegalArgumentException("please fill out Distributor name field");
        }
        if (distributorDescription.isEmpty()) {
            throw new IllegalArgumentException("please fill out Distributor description field");
        }
        return true;
    }

    /**
     * Get TreeSet of Distributors for all that have IDs matching an ID in the parameter list.
     *
     * //TODO: move to SearchEngine.
     *
     * @param distributorChoices String list of IDs
     * @return TreeSet with all distributors with matching IDs to search.
     */
    public static TreeSet<Distributor> getMatchingDistributorsOnIDs(ArrayList<String> distributorChoices) {
        TreeSet<Distributor> result = new TreeSet();
        for (Distributor distributor : distributorList) {
            if (distributorChoices.contains(Integer.toString(distributor.objectID))) {
                result.add(distributor);
            }
        }
        return result;
    }

    /**
     * Find distributor with matching ID to search parameter.
     *
     * //TODO: move to SearchEngine.
     *
     * @param distributorID ID for desired distributor.
     * @return The distributor object if it is found. If not found, a null value is returned.
     */
    public static Distributor findDistributorOnID(int distributorID) {
        for (Distributor distributor : distributorList) {
            if (distributor.objectID == distributorID) {
                return distributor;
            }
        }
        return null;
    }

    /**
     *
     * Get a sorted list of up to ten distributors, based and sorted on amount of category relations to products.
     *
     * @return sorted top ten distributors list.
     */
    public static List<Distributor> topTenDistributors() {
        List<Distributor> distributorProductCounts = new ArrayList(distributorList);

        Collections.sort(distributorProductCounts, new Comparator<Distributor>() {
            @Override
            public int compare(final Distributor o1, final Distributor o2) {
                int o1Size = o1.getDistributorProducts().size();
                int o2Size = o2.getDistributorProducts().size();
                if (o1Size > o2Size) {
                    return -1;
                } else if (o1Size < o2Size) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });

        int subListEnd = 10;
        if (distributorProductCounts.size() < 10) {
            subListEnd = distributorProductCounts.size();
        }

        List<Distributor> result = distributorProductCounts.subList(0, subListEnd);
        return result;
    }

    public boolean addProductToDistributor(Product product) {
        return distributorProducts.add(product);
    }

    public boolean removeProductFromDistributor(Product product) {
        return distributorProducts.remove(product);
    }

    public static TreeSet<Distributor> getDistributorList() {
        return distributorList;
    }

    public static int getTotalDistributorCount() {
        return distributorList.size();
    }

    public TreeSet<Product> getDistributorProducts() {
        return distributorProducts;
    }

    public static void setupDistributorListFromDB(TreeSet<Distributor> distributorListFromDB) {
        distributorList = distributorListFromDB;
    }

    public static void addToDistributorList(Distributor newDistributor) {
        distributorList.add(newDistributor);
    }

    public static boolean deleteDistributor(int distributorID) {
        return distributorList.remove(findDistributorOnID(distributorID));
    }

    public void editDistributor(String distributorTitle, String distributorDescription) {
        this.objectTitle = distributorTitle;
        this.objectDescription = distributorDescription;
    }
}
