<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : transformCal.xsl
    Created on : 16. Oktober 2012, 07:22
    Author     : g8712
    Description:
        Transform old cal xml to new one.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="xml"/>
    <xsl:strip-space elements="*"/>
    <xsl:template match="/">
        <CalendarConfig>
          <xsl:apply-templates/>
        </CalendarConfig>
    </xsl:template>

    <xsl:template match="table[@name='months']">
        <xsl:element name="months">
            <xsl:for-each select="array">
                <xsl:element name="month">
                    <xsl:attribute name="name">
                        <xsl:value-of disable-output-escaping="no" select="./item"/>
                    </xsl:attribute>
                    <xsl:attribute name="days">
                        <xsl:value-of disable-output-escaping="no" select="./item[position() > 1]"/>
                    </xsl:attribute>
                </xsl:element>
            </xsl:for-each>

        </xsl:element>
    </xsl:template>
     <xsl:template match="array[@name='days']">
        <xsl:element name="days">
            <xsl:for-each select="./item">
                <xsl:element name="day">
                    <xsl:attribute name="name">
                        <xsl:value-of disable-output-escaping="no" select="."/>
                    </xsl:attribute>
                </xsl:element>
            </xsl:for-each>

        </xsl:element>
    </xsl:template>
     <xsl:template match="table[@name='specialdays' or @name='anualdays']">
        <xsl:element name="entries">
            <xsl:attribute name="name"><xsl:value-of select="@name"/></xsl:attribute>
             <xsl:for-each select="array">
                <xsl:element name="day">
                    <xsl:attribute name="offset">
                        <xsl:value-of disable-output-escaping="no" select="./item"/>
                    </xsl:attribute>
                    <xsl:attribute name="name">
                        <xsl:value-of disable-output-escaping="no" select="./item[position() > 1]"/>
                    </xsl:attribute>
                        <xsl:value-of disable-output-escaping="no" select="./item[position() > 2]"/>
                </xsl:element>
            </xsl:for-each>
        </xsl:element>
    </xsl:template>
         <xsl:template match="table[@name='moons']">
        <xsl:element name="moons">
             <xsl:for-each select="array">
                <xsl:element name="moon">
                    <xsl:attribute name="name">
                        <xsl:value-of disable-output-escaping="no" select="./item"/>
                    </xsl:attribute>
                     <xsl:attribute name="revolutionInDays">
                        <xsl:value-of disable-output-escaping="no" select="./item[position() = 2]"/>
                    </xsl:attribute>
                     <xsl:attribute name="offset">
                        <xsl:value-of disable-output-escaping="no" select="./item[position() = 3]"/>
                    </xsl:attribute>
                     <xsl:attribute name="color">
                        <xsl:value-of disable-output-escaping="no" select="./item[position() = 4]"/>
                    </xsl:attribute>
                   <xsl:attribute name="size">
                        <xsl:value-of disable-output-escaping="no" select="./item[position() = 5]"/>
                    </xsl:attribute>
                </xsl:element>
            </xsl:for-each>
        </xsl:element>
    </xsl:template>
       <xsl:template match="table[@name='constellation']">
        <xsl:element name="constellations">
             <xsl:for-each select="array">
                <xsl:element name="constellation">
                    <xsl:attribute name="name">
                        <xsl:value-of disable-output-escaping="no" select="./item"/>
                    </xsl:attribute>
                     <xsl:attribute name="revolutionInDays">
                        <xsl:value-of disable-output-escaping="no" select="./item[position() = 2]"/>
                    </xsl:attribute>
                     <xsl:attribute name="offset">
                        <xsl:value-of disable-output-escaping="no" select="./item[position() = 4]"/>
                    </xsl:attribute>
                     <xsl:attribute name="extreme">
                        <xsl:value-of disable-output-escaping="no" select="./item[position() = 3]"/>
                    </xsl:attribute>
                </xsl:element>
            </xsl:for-each>
        </xsl:element>
    </xsl:template>

       <xsl:template match="table[@name='derivations']">
        <xsl:element name="derivations">
             <xsl:for-each select="array">
                <xsl:element name="derivation">
                    <xsl:attribute name="name">
                        <xsl:value-of disable-output-escaping="no" select="./item"/>
                    </xsl:attribute>
                     <xsl:attribute name="acronym">
                        <xsl:value-of disable-output-escaping="no" select="./item[position() = 2]"/>
                    </xsl:attribute>
                     <xsl:attribute name="correctionInYears">
                        <xsl:value-of disable-output-escaping="no" select="./item[position() = 3]"/>
                    </xsl:attribute>
                </xsl:element>
            </xsl:for-each>
        </xsl:element>
    </xsl:template>

    <xsl:template match="item[@name='mykradoriancorrectionindays']">
        <xsl:element name="mykradoriancorrectionindays">
            <xsl:value-of select="."/>
        </xsl:element>
    </xsl:template>

<xsl:template match="item[@name='monthdaymode']">
        <xsl:element name="monthdaymode">
            <xsl:value-of select="."/>
        </xsl:element>
    </xsl:template>



</xsl:stylesheet>
