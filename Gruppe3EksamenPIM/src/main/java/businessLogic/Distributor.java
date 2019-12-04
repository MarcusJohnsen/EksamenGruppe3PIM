package businessLogic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import javafx.util.Pair;

/**
 *
 * @author Marcus
 */
public class Distributor implements Comparable<Distributor> {

    private int distributorID;
    private String distributorName;
    private String distributorDescription;
    private TreeSet<Product> distributorProducts;

    private static TreeSet<Distributor> distributorList = new TreeSet();

    public Distributor(int distributorID, String distributorName, String distributorDescription) {
        this.distributorID = distributorID;
        this.distributorName = distributorName;
        this.distributorDescription = distributorDescription;
        this.distributorProducts = new TreeSet();
    }

    public static boolean validateDistributorInput(String distributorName, String distributorDescription) throws IllegalArgumentException {
        if (distributorName.isEmpty()) {
            throw new IllegalArgumentException("please fill out Distributor name field");
        }
        if (distributorDescription.isEmpty()) {
            throw new IllegalArgumentException("please fill out Distributor description field");
        }
        return true;
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

    public void editDistributor(String name, String description) {
        this.distributorName = name;
        this.distributorDescription = description;
    }

    public static TreeSet<Distributor> getMatchingDistributorsOnIDs(ArrayList<String> distributorChoices) {
        TreeSet<Distributor> result = new TreeSet();
        for (Distributor distributor : distributorList) {
            if (distributorChoices.contains(Integer.toString(distributor.getDistributorID()))) {
                result.add(distributor);
            }
        }
        return result;
    }

    public static Distributor findDistributorOnID(int distributorID) {
        for (Distributor distributor : distributorList) {
            if (distributor.distributorID == distributorID) {
                return distributor;
            }
        }
        return null;
    }

    public static List<Pair<Distributor, Integer>> topTenDistributors() {
        List<Pair<Distributor, Integer>> distributorProductCounts = new ArrayList();

        for (Distributor distributor : distributorList) {
            distributorProductCounts.add(new Pair(distributor, distributor.distributorProducts.size()));
        }

        Collections.sort(distributorProductCounts, new Comparator<Pair<Distributor, Integer>>() {
            @Override
            public int compare(final Pair<Distributor, Integer> o1, final Pair<Distributor, Integer> o2) {
                if (o1.getValue() > o2.getValue()) {
                    return -1;
                } else if (o1.getValue() < o2.getValue()) {
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

        List<Pair<Distributor, Integer>> result = distributorProductCounts.subList(0, subListEnd);
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

    public int getDistributorID() {
        return distributorID;
    }

    public String getDistributorName() {
        return distributorName;
    }

    public String getDistributorDescription() {
        return distributorDescription;
    }

    public static int getTotalDistributorCount() {
        return distributorList.size();
    }

    public TreeSet<Product> getDistributorProducts() {
        return distributorProducts;
    }

    @Override
    public int compareTo(Distributor otherDistributor) {

        int thisID = this.distributorID;
        int otherID = otherDistributor.distributorID;
        return thisID - otherID;

    }
}
