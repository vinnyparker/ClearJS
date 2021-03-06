package clear.cdb.extjs.test;

import org.junit.Test;
import org.w3c.dom.Node;

import com.farata.cdb.annotations.helper.AnnotationsHelper;
import com.farata.example.dto.AssociateDTO;
import com.farata.example.dto.CompanyDTO;

public class HelperTests {
	@Test
	public void storeNameTest() {
		System.out.println(AnnotationsHelper.getStoreNameFull("com.farata.some.aaa.dto.SomeDTO"));
		System.out.println(AnnotationsHelper.getStoreNameShort("com.farata.some.aaa.dto.SomeDTO"));
		System.out.println(AnnotationsHelper.getStoreNameFullGen("com.farata.some.aaa.dto.SomeDTO"));
		System.out.println(AnnotationsHelper.getStorePackage("com.farata.some.aaa.dto.SomeDTO"));
		System.out.println(AnnotationsHelper.getStorePath("com.farata.some.dto.aaa.SomeDTO"));
		System.out.println(AnnotationsHelper.getStorePathByStoreName("uuii.store.aaa.SomeStore"));
		System.out.println("======");
		System.out.println(AnnotationsHelper.getModelNameFull("com.farata.some.dto.SomeDTO"));
		System.out.println(AnnotationsHelper.getModelNameShort("com.farata.some.dto.SomeDTO"));
		System.out.println(AnnotationsHelper.getModelNameFullGen("com.farata.some.dto.SomeDTO"));
		System.out.println(AnnotationsHelper.getModelPackage("com.farata.some.dto.SomeDTO"));
		System.out.println(AnnotationsHelper.getModelPath("com.farata.some.dto.SomeDTO"));
	}
	
	@Test
	public void getBeanPropertiesTest() {
		try {
			AnnotationsHelper.DEBUG = true;
			Node res = AnnotationsHelper.getBeanProperties(AssociateDTO.class.getCanonicalName());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void getBeanPropertyAnnotationTest() {
		AnnotationsHelper.DEBUG = true;
		try {
			Node res = AnnotationsHelper.getBeanPropertyAnnotation(AssociateDTO.class.getName(), "company", "com.farata.dto2extjs.annotations.JSManyToOne");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
