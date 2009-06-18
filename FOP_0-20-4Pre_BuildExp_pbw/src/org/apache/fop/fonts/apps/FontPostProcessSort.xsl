<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="xml"/>

<xsl:template match="@*|node()">
	<xsl:copy>
		<xsl:apply-templates select="@*|node()"/>
	</xsl:copy>
</xsl:template>

<xsl:template match="widths">
	<xsl:copy>
		<xsl:apply-templates select="char">
			<xsl:sort select="@name" case-order="upper-first"/>
		</xsl:apply-templates>
	</xsl:copy>
</xsl:template>


</xsl:stylesheet>