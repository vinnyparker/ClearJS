package com.farata.dto2extjs.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * Is used to maintain link between the auto-incremented properties of the parent and their "foreign key" copy in the children
 * records.
 * <p>
 *  <table class="innertable">
 *   <tr>
 *   	<th>Parameter</th><th>Type</th><th>Required</th><th>Description</th>
 *   </tr>
 *   <tr>
 *   	<td><code>parent</code></td><td>String</td><td>Required</td>
 *   	<td>Name of the parent bean class
 *   	</td>
 *   </tr>
 *   <tr>
 *   	<td><code>property</code></td><td>String</td><td>Required</td>
 *   	<td>Name of the auto-incremented property (in the <code>parent</code>) that
 * is many-to-one related to this (annotated) property
 *   	</td>
 *   </tr>
 *  </table>
 *  </p>  
 * <p>
 * Transaction that includes inserts of the parent and children records into two related tables, may need a special treatment when the primary keys of the parent 
 * are auto-incremented or otherwise automatically modified by the database.
 * </p>
 * These auto-incremented values are available only upon completion of INSERT of the parent, and yet, they must be copied to each child,
 * where they represent foreign keys. Importantly, that has to be done prior to insertion of the children into the database to
 * adhere to referential constraints. 
 * </p>
 * <p>ClearDataBuilder technique to handle this requirement assumes that client (JavaScript code) should assign 
 * corresponding properties of the parent and it's children a temporary place-holder value so that
 * the server (Java code) has to replace it with the proper one, obtained upon parent's INSERT. See <a href="http://www.cleartoolkit.com/dokuwiki/doku.php?id=clearwiki:20.cleardatabuilder:03.authoring_java_dto:07.autoincrement_with_fxmanytoone">Supporting Auto-incremented Properties with &#64;FXManyToOne and Local Flex Identities</a>
 * </p>
 * <p>
 * For instance, assuming use 
 * case of <code>CompanyDTO</code> and <code>CompanyAssociateDTO</code>,
 * where company has many associates, if <code>companyDTO.id</code> contains -1, or -2, -3 etc., then every 
 * subordinate <code>CompanyAssociateDTO</code> that arrives for the same transaction should carry -1, or -2, -3 etc. in it's 
 * property responsible for <code>id</code> of the parent company, correspondingly. 
 * </p>
 * <p>Java service code, generated by CDB, stores auto-incremented values in the internal map, by the key composed of:
 * <li>bean class name</li>
 * <li>auto-incremented property name</i>
 * <li>original(temporary) value.</li>
 * </p>
 * <p>
 * Then, prior to insert of the children, the corresponding properties of the children get replaced with the new ones from the map,
 * provided the class name, property name and original value are known. That's where <code>&#64;JSManyToOne</code> gets handy:</p> 
 * <pre>
 *	&#64;JSManyToOne(parent="com.farata.test.entity.Company", property="id")
	public Company getCompany() {
		return this.company;
	}
 * </pre>
 * <p>This annotation indicates the name of the parent bean class and the property that is auto-incremented(in the parent) - constituting the 
 * first two parts of the key. The last part is the current value of the annotated property itself. Once the key is matched the property value
 * gets replaced with the stored auto-incremented value.
 * </p>
 */ 
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target( { ElementType.METHOD, ElementType.FIELD })
public @interface JSManyToOne {
	public abstract Class<?> parent();
	public abstract String property();
}