<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xalan="http://xml.apache.org/xslt"
	xmlns:helper="xalan://com.farata.cdb.annotations.helper.AnnotationsHelper"
	exclude-result-prefixes="xalan">

	<xsl:output omit-xml-declaration="yes" method="text" />
	<xsl:include href="service-dto-jpql.xsl" />
	<xsl:include href="service-dto-get.xsl" />
	<xsl:include href="service-dto-fill-children.xsl" />

	<xsl:template match="/" name="service-dto.xsl">
		<xsl:param name="dtoName" />
		<xsl:param name="rootPackage" />
		<xsl:param name="interfaceName" />
		<xsl:param name="methodName" />
		<xsl:variable name="methodNode"
			select="annotated-types/annotated-type[@name=$interfaceName]/methods/method[@name=$methodName]" />
		<xsl:variable name="jpqlMethodNode"
			select="$methodNode/annotations/annotation[@name='clear.cdb.extjs.annotations.CX_JSJPQLMethod']" />
		<xsl:variable name="getMethodNode"
			select="$methodNode/annotations/annotation[@name='clear.cdb.extjs.annotations.CX_JSGetMethod']" />
		<xsl:variable name="fillChildrenMethodNode"
			select="$methodNode/annotations/annotation[@name='clear.cdb.extjs.annotations.CX_JSFillChildrenMethod']" />

		<xsl:choose>
			<xsl:when test="$jpqlMethodNode">
				<xsl:call-template name="service-dto-jpql.xsl">
					<xsl:with-param name="dtoName" select="$dtoName" />
					<xsl:with-param name="rootPackage" select="$rootPackage" />
					<xsl:with-param name="interfaceName" select="$interfaceName" />
					<xsl:with-param name="methodName" select="$methodName" />
				</xsl:call-template>
			</xsl:when>
			<xsl:when test="$getMethodNode">
				<xsl:call-template name="service-dto-get.xsl">
					<xsl:with-param name="dtoName" select="$dtoName" />
					<xsl:with-param name="rootPackage" select="$rootPackage" />
					<xsl:with-param name="interfaceName" select="$interfaceName" />
					<xsl:with-param name="methodName" select="$methodName" />
				</xsl:call-template>
			</xsl:when>
			<xsl:when test="$fillChildrenMethodNode">
				<xsl:call-template name="service-dto-fill-children.xsl">
					<xsl:with-param name="dtoName" select="$dtoName" />
					<xsl:with-param name="rootPackage" select="$rootPackage" />
					<xsl:with-param name="interfaceName" select="$interfaceName" />
					<xsl:with-param name="methodName" select="$methodName" />
				</xsl:call-template>
			</xsl:when>
		</xsl:choose>
	</xsl:template>
</xsl:stylesheet>