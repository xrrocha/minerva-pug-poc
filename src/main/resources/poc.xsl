<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="3.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:template match="topic">
        <html>
            <head>
                <title>
                    <xsl:value-of select="title"/>
                </title>
            </head>
            <body>
                <xsl:apply-templates select="body"/>
            </body>
        </html>
    </xsl:template>

    <xsl:template match="snippet">
        <code language="{@language}">
            <pre>
                <xsl:apply-templates/>
            </pre>
        </code>
    </xsl:template>

    <xsl:template match="@*|node()">
        <xsl:copy>
            <xsl:apply-templates select="@*|node()"/>
        </xsl:copy>
    </xsl:template>
</xsl:stylesheet>