<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">



<xsl:variable name="root_context">/iccs</xsl:variable>



<xsl:variable name="xml_context" select="concat($root_context,'/xml')"/>

<xsl:variable name="xsl_context" select="concat($root_context,'/xsl')"/>

<xsl:variable name="js_context" select="concat($root_context,'/js')"/>

<xsl:variable name="css_context" select="concat($xsl_context,'/css')"/>



</xsl:stylesheet>

