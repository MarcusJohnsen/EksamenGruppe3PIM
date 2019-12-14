package businessLogic;

import org.junit.Test;
import static org.junit.Assert.*;

public class BundleTest {

    @Test
    public void testNegativeFindBundleOnID() {
        int bundleIDNotInList = 500;
        Bundle result = Bundle.findBundleOnID(bundleIDNotInList);

        assertNull(result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeValidateBundleInput_EmptyBundleName() {
        String bundleName = "";
        String bundleDescription = "description";
        Integer bundleID = 1;

        Bundle.validateBundleInput(bundleName, bundleDescription, bundleID);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeValidateBundleInput_EmptyBundleDescription() {
        String bundleName = "Name";
        String bundleDescription = "";
        Integer bundleID = 1;

        Bundle.validateBundleInput(bundleName, bundleDescription, bundleID);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeValidateBundleInput_DublicateName_NullBundleID() {
        Bundle bundle = new Bundle(1, "Name", "Description", null);
        Bundle.addToBundleList(bundle);

        String bundleName = "Name";
        String bundleDescription = "OtherDescription";
        Integer bundleID = null;

        Bundle.validateBundleInput(bundleName, bundleDescription, bundleID);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeValidateBundleInput_DublicateName_NotMatchingBundleID() {
        Bundle bundle = new Bundle(1, "Name", "Description", null);
        Bundle.addToBundleList(bundle);

        String bundleName = "Name";
        String bundleDescription = "OtherDescription";
        Integer bundleID = 2;

        Bundle.validateBundleInput(bundleName, bundleDescription, bundleID);
    }

    @Test
    public void testValidateBundleInput_DublicateName_MatchingBundleID() {
        Bundle bundle = new Bundle(1, "Name", "Description", null);
        Bundle.addToBundleList(bundle);

        String bundleName = "Name";
        String bundleDescription = "OtherDescription";
        Integer bundleID = 1;

        Bundle.validateBundleInput(bundleName, bundleDescription, bundleID);
    }

}
