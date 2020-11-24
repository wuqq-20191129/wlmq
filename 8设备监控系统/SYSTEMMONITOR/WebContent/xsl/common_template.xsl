<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:output method="xml"/>

<xsl:template name="option_list">
	<xsl:param name="selectvalue"/>
	<xsl:param name="xml"/>
	<xsl:for-each select="$xml/list/item">
		<xsl:sort select="code"/>
		<xsl:choose>
			<xsl:when test="$selectvalue = code">
				<option value="{code}" selected="true">
					<xsl:choose>
						<xsl:when test="desc=''">
							<xsl:value-of select="code"/>
						</xsl:when>
						<xsl:otherwise>
							<xsl:value-of select="desc"/>
						</xsl:otherwise>
					</xsl:choose>
				</option>
			</xsl:when>
			<xsl:otherwise>
				<option value="{code}">
					<xsl:choose>
						<xsl:when test="desc=''">
							<xsl:value-of select="code"/>
						</xsl:when>
						<xsl:otherwise>
							<xsl:value-of select="desc"/>
						</xsl:otherwise>
					</xsl:choose>
				</option>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:for-each>
</xsl:template>

<xsl:template name="option_list_gen">
	<xsl:param name="selectvalue"/>
	<xsl:param name="root"/>
	<xsl:param name="code"/>
	<xsl:param name="desc"/>
	
	<xsl:for-each select="$root">
	<xsl:sort select="groupID"/>
		<xsl:choose>
			<xsl:when test="$selectvalue = groupID">
				<option value="{groupID}" selected="true">

					<xsl:choose>
						<xsl:when test="groupName=''">
							<xsl:value-of select="groupID"/>
						</xsl:when>
						<xsl:otherwise>
							<xsl:value-of select="groupName"/>
						</xsl:otherwise>
					</xsl:choose>
				
				</option>
			</xsl:when>
			<xsl:otherwise>
				<option value="{groupID}">
					<xsl:choose>
						<xsl:when test="groupName=''">
							<xsl:value-of select="groupID"/>
						</xsl:when>
						<xsl:otherwise>
							<xsl:value-of select="groupName"/>
						</xsl:otherwise>
						
						
					</xsl:choose>
				
				</option>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:for-each>
</xsl:template>
<xsl:template name="option_list_pub_bynum">
	<xsl:param name="selectvalue" select="''"/>
	<xsl:param name="defValue" select="''"/>
	<xsl:param name="root"/>
	<xsl:param name="isAll" select="1"/>
	<xsl:param name="filter_1" select="''"/>
	<xsl:param name="filter_2" select="''"/>
	<xsl:param name="filter_3" select="''"/>
	<xsl:param name="filter_4" select="''"/>
	<xsl:param name="filter_5" select="''"/>
	
	

	<xsl:if test="$isAll=1">
		<option value="{$defValue}">=======</option>
	</xsl:if>
	
	<xsl:for-each select="$root">
	 <xsl:sort select="code" data-type="number"/> 
	  <xsl:if test="(not(code =$filter_1))and (not(code =$filter_2)) and (not(code =$filter_3)) and (not(code =$filter_4)) and (not(code =$filter_5)) ">  
		<xsl:choose>
			<xsl:when test="($selectvalue = code) or (@isDefaultValue='true')">
				<option value="{code}" selected="true">

					<xsl:choose>
						<xsl:when test="codeText=''">
							<xsl:value-of select="code"/>
						</xsl:when>
						<xsl:otherwise>
							<xsl:value-of select="codeText"/>
						</xsl:otherwise>
					</xsl:choose>
				
				</option>
			</xsl:when>
			<xsl:otherwise>
				<option value="{code}">
					<xsl:choose>
						<xsl:when test="codeText=''">
							<xsl:value-of select="code"/>
						</xsl:when>
						<xsl:otherwise>
							<xsl:value-of select="codeText"/>
						</xsl:otherwise>
						
						
					</xsl:choose>
				
				</option>
			</xsl:otherwise>
		</xsl:choose>
		</xsl:if>
	</xsl:for-each>
</xsl:template>

<xsl:template name="option_list_pub">
	<xsl:param name="selectvalue" select="''"/>
	<xsl:param name="defValue" select="''"/>
	<xsl:param name="root"/>
	<xsl:param name="isAll" select="1"/>
	<xsl:param name="filter_1" select="''"/>
	<xsl:param name="filter_2" select="''"/>
	<xsl:param name="filter_3" select="''"/>
	<xsl:param name="filter_4" select="''"/>
	<xsl:param name="filter_5" select="''"/>
	
	

	<xsl:if test="$isAll=1">
		<option value="{$defValue}">=======</option>
	</xsl:if>
	
	<xsl:for-each select="$root">
	<xsl:sort select="code"/>
	  <xsl:if test="(not(code =$filter_1))and (not(code =$filter_2)) and (not(code =$filter_3)) and (not(code =$filter_4)) and (not(code =$filter_5)) ">  
		<xsl:choose>
			<xsl:when test="($selectvalue = code) or (@isDefaultValue='true')">
				<option value="{code}" selected="true">

					<xsl:choose>
						<xsl:when test="codeText=''">
							<xsl:value-of select="code"/>
						</xsl:when>
						<xsl:otherwise>
							<xsl:value-of select="codeText"/>
						</xsl:otherwise>
					</xsl:choose>
				
				</option>
			</xsl:when>
			<xsl:otherwise>
				<option value="{code}">
					<xsl:choose>
						<xsl:when test="codeText=''">
							<xsl:value-of select="code"/>
						</xsl:when>
						<xsl:otherwise>
							<xsl:value-of select="codeText"/>
						</xsl:otherwise>
						
						
					</xsl:choose>
				
				</option>
			</xsl:otherwise>
		</xsl:choose>
		</xsl:if>
	</xsl:for-each>
</xsl:template>
<xsl:template name="option_list_pub_limit">
	<xsl:param name="selectvalue"/>
	<xsl:param name="defValue" select="''"/>
	<xsl:param name="root"/>
	<xsl:param name="isAll" select="1"/>
	<xsl:param name="filter_1" select="''"/>
	<xsl:param name="filter_2" select="''"/>
	<xsl:param name="filter_3" select="''"/>
	<xsl:param name="filter_4" select="''"/>
	<xsl:param name="filter_5" select="''"/>
	<xsl:param name="filter_6" select="''"/>
	
	

	<xsl:if test="$isAll=1">
		<option value="{$defValue}">=======</option>
	</xsl:if>
	
	<xsl:for-each select="$root">
	<xsl:sort select="code"/>
	  <xsl:if test="(code =$filter_1)or (code =$filter_2) or (code =$filter_3) or  (code =$filter_4) or  (code =$filter_5) or  (code =$filter_6)">  
		<xsl:choose>
			<xsl:when test="$selectvalue = code">
				<option value="{code}" selected="true">

					<xsl:choose>
						<xsl:when test="codeText=''">
							<xsl:value-of select="code"/>
						</xsl:when>
						<xsl:otherwise>
							<xsl:value-of select="codeText"/>
						</xsl:otherwise>
					</xsl:choose>
				
				</option>
			</xsl:when>
			<xsl:otherwise>
				<option value="{code}">
					<xsl:choose>
						<xsl:when test="codeText=''">
							<xsl:value-of select="code"/>
						</xsl:when>
						<xsl:otherwise>
							<xsl:value-of select="codeText"/>
						</xsl:otherwise>
						
						
					</xsl:choose>
				
				</option>
			</xsl:otherwise>
		</xsl:choose>
		</xsl:if>
	</xsl:for-each>
</xsl:template>



<xsl:template name="option_list_pub_none">
	<xsl:param name="selectvalue"/>
	<xsl:param name="root"/>
	<xsl:param name="defValue" select="''"/>
	<option value="{$defValue}">=======</option>
</xsl:template>

<xsl:template name="option_list_pub_none_all">
	<xsl:param name="selectvalue"/>
	<xsl:param name="root"/>
	<xsl:param name="defValue" select="''"/>
	<option value="{$defValue}">全部</option>
</xsl:template>

<xsl:template name="option_list_pub_none_1">
	<xsl:param name="selectvalue"/>
	<xsl:param name="root"/>
	<xsl:param name="defValue" select="''"/>
	<xsl:param name="opValue" select="''"/>
	<xsl:param name="opText" select="''"/>
	<option value="{$defValue}">=======</option>
	<option value="{$opValue}"><xsl:value-of select="$opText"/></option>
</xsl:template>

<xsl:template name="option_values">
	<xsl:param name="root"/>
	<xsl:param name="inputName"/>
	<!--1：去除所有主类型的至多3个固定选项 0：去至多3主类型的某一个固定选项  -->
	<xsl:param name="filter_type_all" select="1"/>
	<xsl:param name="filter_type_1" select="''"/>
	<!--主类型ID -->
	<xsl:param name="filter_type_2" select="''"/>
	<xsl:param name="filter_type_3" select="''"/>
	<!--去除的选项I -->
	<xsl:param name="filter_1" select="''"/>
	<xsl:param name="filter_2" select="''"/>
	<xsl:param name="filter_3" select="''"/>
		<input type="hidden" id="{$inputName}" name="{$inputName}">
		  	<xsl:attribute name="value">
		  		<xsl:for-each select="$root">
		  			<xsl:if test="(($filter_type_all=1) and (not(code =$filter_1))and (not(code =$filter_2)) and (not(code =$filter_3)))or (($filter_type_all=0)and not(strType=$filter_type_1) and not(strType=$filter_type_2) and not(strType=$filter_type_3)) or (($filter_type_all=0)and (strType=$filter_type_1) and not(code=$filter_1)) or(($filter_type_all=0)and (strType=$filter_type_2) and not(code=$filter_2)) or(($filter_type_all=0)and (strType=$filter_type_3) and not(code=$filter_3))">  
		  				<xsl:value-of select="concat(strType,',',code,',',codeText,':')" />
		  			</xsl:if>
		  		</xsl:for-each>
		    </xsl:attribute>
		</input>
</xsl:template>
<xsl:template name="option_values_limit_part">
	<xsl:param name="root"/>
	<xsl:param name="inputName"/>
	<!--1：仅最多取3个主类型，前2个主类型限制一个，第3个主类型不限制  -->
	<xsl:param name="filter_type_all" select="1"/>
	<xsl:param name="filter_type_1" select="''"/>
	<!--主类型ID -->
	<xsl:param name="filter_type_2" select="''"/>
	<xsl:param name="filter_type_3" select="''"/>
	<!--去除的选项I -->
	<xsl:param name="filter_1" select="''"/>
	<xsl:param name="filter_2" select="''"/>
	<xsl:param name="filter_3" select="''"/>
		<input type="hidden" id="{$inputName}" name="{$inputName}">
		  	<xsl:attribute name="value">
		  		<xsl:for-each select="$root">
		  		<!--	<xsl:if test="(($filter_type_all=1) and (not(code =$filter_1))and (not(code =$filter_2)) and (not(code =$filter_3)))or (($filter_type_all=0)and not(strType=$filter_type_1) and not(strType=$filter_type_2) and not(strType=$filter_type_3)) or (($filter_type_all=0)and (strType=$filter_type_1) and not(code=$filter_1)) or(($filter_type_all=0)and (strType=$filter_type_2) and not(code=$filter_2)) or(($filter_type_all=0)and (strType=$filter_type_3) and not(code=$filter_3))">   -->
		  			<xsl:if test="(((strType=$filter_type_1)and(code=$filter_1))or((strType=$filter_type_2)and(code=$filter_2))or(strType=$filter_type_3))">
		  				<xsl:value-of select="concat(strType,',',code,',',codeText,':')" />
		  			</xsl:if>
		  		</xsl:for-each>
		    </xsl:attribute>
		</input>
</xsl:template>

<xsl:template name="option_values_limit_one">
	<xsl:param name="root"/>
	<xsl:param name="inputName"/>
	<!--仅保留一主类型3个子类型  -->
  <!--作限制的主类型ID -->
	<xsl:param name="filter_type_1" select="''"/>
	

	<!--保留的子类型选项，至多3个 -->
	<xsl:param name="filter_1" select="''"/>
	<xsl:param name="filter_2" select="''"/>
	<xsl:param name="filter_3" select="''"/>
		<input type="hidden" id="{$inputName}" name="{$inputName}">
		  	<xsl:attribute name="value">
		  		<xsl:for-each select="$root">
		  		<xsl:choose>
		  			<xsl:when test="strType=$filter_type_1">
		  				<xsl:if test="(code=$filter_1)or(code=$filter_2)or(code=$filter_3)">
		  					<xsl:value-of select="concat(strType,',',code,',',codeText,':')" />
		  				</xsl:if>
		  			</xsl:when>
		  			<xsl:otherwise>
		  				<xsl:value-of select="concat(strType,',',code,',',codeText,':')" />
		  			</xsl:otherwise>
		  		</xsl:choose>
		  		</xsl:for-each>
		    </xsl:attribute>
		</input>
</xsl:template>

<xsl:template name="option_values_limit">
	<xsl:param name="root"/>
	<xsl:param name="inputName"/>
	<xsl:param name="filter_1" select="''"/>
	<xsl:param name="filter_2" select="''"/>
	<xsl:param name="filter_3" select="''"/>
		<input type="hidden" id="{$inputName}" name="{$inputName}">
		  	<xsl:attribute name="value">
		  		<xsl:for-each select="$root">
		  			<xsl:if test="(code =$filter_1)or (code =$filter_2) or (code =$filter_3)">  
		  				<xsl:value-of select="concat(strType,',',code,',',codeText,':')" />
		  			</xsl:if>
		  		</xsl:for-each>
		    </xsl:attribute>
		</input>
</xsl:template>

<!-- tvm停售模块的小时时间模板 -->
<xsl:template name="hour_template">
	<option value="00">00</option>
	<option value="01">01</option>
	<option value="02">02</option>
	<option value="03">03</option>
	<option value="04">04</option>
	<option value="05">05</option>
	<option value="06">06</option>
	<option value="07">07</option>
	<option value="08">08</option>
	<option value="09">09</option>
	<option value="10">10</option>
	<option value="11">11</option>
	<option value="12">12</option>
	<option value="13">13</option>
	<option value="14">14</option>
	<option value="15">15</option>
	<option value="16">16</option>
	<option value="17">17</option>
	<option value="18">18</option>
	<option value="19">19</option>
	<option value="20">20</option>
	<option value="21">21</option>
	<option value="22">22</option>
	<option value="23">23</option>
</xsl:template>

<!-- tvm停售模块的分钟时间模板 -->
<xsl:template name="min_template">
	<option value="00">00</option>
	<option value="01">01</option>
	<option value="02">02</option>
	<option value="03">03</option>
	<option value="04">04</option>
	<option value="05">05</option>
	<option value="06">06</option>
	<option value="07">07</option>
	<option value="08">08</option>
	<option value="09">09</option>
	<option value="10">10</option>
	<option value="11">11</option>
	<option value="12">12</option>
	<option value="13">13</option>
	<option value="14">14</option>
	<option value="15">15</option>
	<option value="16">16</option>
	<option value="17">17</option>
	<option value="18">18</option>
	<option value="19">19</option>
	<option value="20">20</option>
	<option value="21">21</option>
	<option value="22">22</option>
	<option value="23">23</option>
	<option value="24">24</option>
	<option value="25">25</option>
	<option value="26">26</option>
	<option value="27">27</option>
	<option value="28">28</option>
	<option value="29">29</option>
	<option value="30">30</option>
	<option value="31">31</option>
	<option value="32">32</option>
	<option value="33">33</option>
	<option value="34">34</option>
	<option value="35">35</option>
	<option value="36">36</option>
	<option value="37">37</option>
	<option value="38">38</option>
	<option value="39">39</option>
	<option value="40">40</option>
	<option value="41">41</option>
	<option value="42">42</option>
	<option value="43">43</option>
	<option value="44">44</option>
	<option value="45">45</option>
	<option value="46">46</option>
	<option value="47">47</option>
	<option value="48">48</option>
	<option value="49">49</option>
	<option value="50">50</option>
	<option value="51">51</option>
	<option value="52">52</option>
	<option value="53">53</option>
	<option value="54">54</option>
	<option value="55">55</option>
	<option value="56">56</option>
	<option value="57">57</option>
	<option value="58">58</option>
	<option value="59">59</option>
</xsl:template>

<xsl:template name="generate_report_decision__month_caption">
	<xsl:param name="isCheck" select="0"/>
		<input type="radio" name="selectedDate" >
			<xsl:if test="$isCheck=1">
			   <xsl:attribute name="checked">
			   		true
			   </xsl:attribute>
			</xsl:if>
		</input>
				按年统计				
</xsl:template>
<xsl:template name="generate_report_decision_week_caption">
	<xsl:param name="isCheck" select="0"/>
		<input type="radio" name="selectedDate" >
			<xsl:if test="$isCheck=1">
			   <xsl:attribute name="checked">
			   		true
			   </xsl:attribute>
			</xsl:if>
		</input>
				按周统计				
</xsl:template>
<xsl:template name="generate_report_decision_month_with_event">
	<xsl:param name="formName" select="''"/>
	<xsl:param name="relativeRadioIndex" select="-1"/>

	
	<select name="monthWeek" onChange="setReportDecisionWeek('{$formName}','yearWeek','monthWeek','week');">
	   <xsl:attribute name="relRadioIndex">
			<xsl:value-of select="$relativeRadioIndex"/>
		</xsl:attribute>
		 <option value="">==</option>
		 <xsl:call-template name="option_list_pub">
					   <xsl:with-param name="root" select="/Service/Result/Months/pubFlagVO"/>
					   <xsl:with-param name="isAll" select="0"/>
			</xsl:call-template>
	</select>				
</xsl:template>

<xsl:template name="generate_report_decision_week_control">
<xsl:param name="relativeRadioIndex" select="-1"/>
	<select name="week">
		<xsl:attribute name="relRadioIndex">
			<xsl:value-of select="$relativeRadioIndex"/>
		</xsl:attribute>
		<option value="">=====</option>
	</select>				
</xsl:template>

<xsl:template name="generate_report_decision__datespan_caption">
	<xsl:param name="isCheck" select="0"/>
		<input type="radio" name="selectedDate" >
			<xsl:if test="$isCheck=1">
			   <xsl:attribute name="checked">
			   		true
			   </xsl:attribute>
			</xsl:if>
		</input>
				按时间段统计				
</xsl:template>

<xsl:template name="generate_report_decision_date_caption">
	<xsl:param name="isCheck" select="0"/>
		<input type="radio" name="selectedDate">
			<xsl:if test="$isCheck=1">
			   <xsl:attribute name="checked">
			   		true
			   </xsl:attribute>
			</xsl:if>
		</input>
				按月统计				
</xsl:template>
<xsl:template name="generate_report_decision_hour_caption">
	<xsl:param name="isCheck" select="0"/>
		<input type="radio" name="selectedDate">
			<xsl:if test="$isCheck=1">
			   <xsl:attribute name="checked">
			   		true
			   </xsl:attribute>
			</xsl:if>
		</input>
				按日统计				
</xsl:template>
<xsl:template name="generate_report_decision_hourse_caption">
	<xsl:param name="isCheck" select="0"/>
		<input type="radio" name="selectedDate" >
			<xsl:if test="$isCheck=1">
			   <xsl:attribute name="checked">
			   		true
			   </xsl:attribute>
			</xsl:if>
		</input>
				按小时统计				
</xsl:template>
<xsl:template name="generate_statistic_button">
	<xsl:param name="clickMethod"/>
  <input type="button" id="btStatistic" name="statistic"  value="统计" class="buttonStyle" onclick="{$clickMethod}" />
</xsl:template>

<!-- 报表系统 年、月、日报检索 模板 -->
<xsl:template name="baobiao_template">
	<tr class="trContent">
		<td width="45%">
		  <input type="radio" name="radio1"/>年报检索:
	    <select name="Yselect">
		  <option value="2003">2003</option>
		  <option value="2004">2004</option>
		  <option value="2005">2005</option>
		  <option value="2006">2006</option>
		  <option value="2007">2007</option>
		  <option value="2008">2008</option>
		  <option value="2009">2009</option>
		  <option value="2010">2010</option>
	    </select>
	    年 
	    </td>
	</tr>

	<tr class="trContent">
		<td width="45%">
		 <input type="radio" name="radio1"/>月报检索:
		<select name="Mselect">
		  <option value="2003">2003</option>
		  <option value="2004">2004</option>
		  <option value="2005">2005</option>
		  <option value="2006">2006</option>
		  <option value="2007">2007</option>
		  <option value="2008">2008</option>
		  <option value="2009">2009</option>
		  <option value="2010">2010</option>
                </select>
		年 
		<select name="Mselect">
		  <option value="0"></option>
		  <option value="1">1</option>
		  <option value="2">2</option>
		  <option value="3">3</option>
		  <option value="4">4</option>
		  <option value="5">5</option>
		  <option value="6">6</option>
		  <option value="7">7</option>
		  <option value="8">8</option>
		  <option value="9">9</option>
		  <option value="10">10</option>
		  <option value="11">11</option>
		  <option value="12">12</option>
		</select> 
		月
		</td>
	</tr>

	<tr class="trContent">
		<td width="45%">
		 <input type="radio" name="radio1"/>日报检索:
		<select name="Dselect">
		  <option value="2003">2003</option>
		  <option value="2004">2004</option>
		  <option value="2005">2005</option>
		  <option value="2006">2006</option>
		  <option value="2007">2007</option>
		  <option value="2008">2008</option>
		  <option value="2009">2009</option>
		  <option value="2010">2010</option>
	        </select>
		年
		<select name="Dselect">
		  <option value="1">1</option>
		  <option value="2">2</option>
		  <option value="3">3</option>
		  <option value="4">4</option>
		  <option value="5">5</option>
		  <option value="6">6</option>
		  <option value="7">7</option>
		  <option value="8">8</option>
		  <option value="9">9</option>
		  <option value="10">10</option>
		  <option value="11">11</option>
		  <option value="12">12</option>
		</select>
		月
		<select name="Dselect">
		  <option value="0"></option>
		  <option value="1">1</option>
		  <option value="2">2</option>
		  <option value="3">3</option>
		  <option value="4">4</option>
		  <option value="5">5</option>
		  <option value="6">6</option>
		  <option value="7">7</option>
		  <option value="8">8</option>
		  <option value="9">9</option>
		  <option value="10">10</option>
		  <option value="11">11</option>
		  <option value="12">12</option>
		    <option value="13">13</option>
		  <option value="14">14</option>
		  <option value="15">15</option>
		  <option value="16">16</option>
		  <option value="17">17</option>
		  <option value="18">18</option>
		  <option value="19">19</option>
		  <option value="20">20</option>
		  <option value="21">21</option>
		  <option value="22">22</option>
		  <option value="23">23</option>
		  <option value="24">24</option>
		    <option value="25">25</option>
		  <option value="26">26</option>
		  <option value="27">27</option>
		  <option value="28">28</option>
		  <option value="29">29</option>
		  <option value="30">30</option>
		</select>
		日
		</td>
		<td>
 <input type="button" name="Submit" value="确定" onclick="showtable()"/></td>
	</tr>
</xsl:template>

<!-- 决策支持系统od统计 年、月、日报检索 模板 -->
<xsl:template name="jczhc_template">
<table width="95%" align="center" class="simpleTable">
       <tr class="trContent">
	 <td width="10%" align="left"><input type="radio"  name="radio" checked="1" value="" />按月统计:</td>
	 <td width="10%">
	     <select name="select">
		  <option>2003</option>
		  <option>2004</option>
		  <option>2005</option>
		  <option>2006</option>
		  <option>2007</option>
		  <option>2008</option>
		  <option>2009</option>
		  <option>2010</option>
	    </select>
	    年 
	 </td>
	 <td width="8%">
	 </td>
	 <td width="10%">
	 </td>
	 <td width="62%">
	 </td>
	</tr>
	
	
	<tr class="trContent">
	   <td width="10%" align="left"><input type="radio"  name="radio"   value="" />按日统计:</td>
	   <td width="10%">
	      <select name="select">
                 <option>2003</option>
                 <option>2004</option>
                 <option>2005</option>
                 <option>2006</option>
                 <option>2007</option>
                 <option>2008</option>
                 <option>2009</option>
                 <option>2010</option>
             </select>
             年 
           </td>
           <td width="8%">
           <select name="select">
              <option>1</option>
              <option>2</option>
              <option>3</option>
              <option>4</option>
              <option>5</option>
              <option>6</option>
              <option>7</option>
              <option>8</option>
              <option>9</option>
              <option>10</option>
              <option>11</option>
              <option>12</option>
           </select> 
           月
           </td>
           <td width="10%"></td>
           <td width="62%"></td>
	</tr>
	
	
       <tr class="trContent">
          <td width="10%" align="left">
	     <input type="radio"  name="radio"   value="" />按小时统计:
	  </td>
          <td width="10%">
	     <select name="select">
               <option>2003</option>
               <option>2004</option>
               <option>2005</option>
               <option>2006</option>
               <option>2007</option>
               <option>2008</option>
               <option>2009</option>
               <option>2010</option>
             </select>
             年
          </td>
           <td width="8%">
           <select name="select">
             <option>1</option>
             <option>2</option>
             <option>3</option>
             <option>4</option>
             <option>5</option>
             <option>6</option>
             <option>7</option>
             <option>8</option>
             <option>9</option>
             <option>10</option>
             <option>11</option>
             <option>12</option>
           </select>
           月 </td>
           <td width="10%">
           <select name="select">
             <option>1</option>
             <option>2</option>
             <option>3</option>
             <option>4</option>
             <option>5</option>
             <option>6</option>
             <option>7</option>
             <option>8</option>
             <option>9</option>
             <option>10</option>
             <option>11</option>
             <option>12</option>
             <option>13</option>
             <option>14</option>
             <option>15</option>
             <option>16</option>
             <option>17</option>
             <option>18</option>
             <option>19</option>
             <option>20</option>
             <option>21</option>
             <option>22</option>
             <option>23</option>
             <option>24</option>
             <option>25</option>
             <option>26</option>
             <option>27</option>
             <option>28</option>
             <option>29</option>
             <option>30</option>
             <option>31</option>
           </select> 
           日</td>

</tr>
</table>
</xsl:template>


<!-- 决策支持系统收益分析 年、月 报检索 模板 -->
<xsl:template name="jczhc_sy_template">
<table width="95%" align="center" class="simpleTable">
       <tr class="trContent">
	 <td width="10%" align="left"><input type="radio"  name="radio" checked="1" value="" />按月统计:</td>
	 <td width="10%">
	     <select name="year">
		  <option>2003</option>
		  <option>2004</option>
		  <option>2005</option>
		  <option>2006</option>
		  <option>2007</option>
		  <option>2008</option>
		  <option>2009</option>
		  <option>2010</option>
	    </select>
	    年 
	 </td>
	 <td width="8%">
	 </td>
	 <td width="10%">
	 </td>
	 <td width="62%">
	 </td>
	</tr>
	
	
	<tr class="trContent">
	   <td width="10%" align="left"><input type="radio"  name="radio"   value="" />按日统计:</td>
	   <td width="10%">
	      <select name="year">
                 <option>2003</option>
                 <option>2004</option>
                 <option>2005</option>
                 <option>2006</option>
                 <option>2007</option>
                 <option>2008</option>
                 <option>2009</option>
                 <option>2010</option>
             </select>
             年 
           </td>
           <td width="8%">
           <select name="months">
              <option>01</option>
              <option>02</option>
              <option>03</option>
              <option>04</option>
              <option>05</option>
              <option>06</option>
              <option>07</option>
              <option>08</option>
              <option>09</option>
              <option>10</option>
              <option>11</option>
              <option>12</option>
           </select> 
           月
           </td>
           <td width="10%"></td>
           <td width="62%"></td>
	</tr>
	
	
    
</table>

</xsl:template>


<xsl:template name="ic_button_list">
<table width="95%" align="center" class="simpleTalbe">
	<tr>
	  <td>
		  <input type="button" name="Submit" value="增加" class="buttonStyle"/> 
		  <input type="button" name="Submit" value="修改" class="buttonStyle"/> 
		  <input type="button" name="Submit" value="删除" class="buttonStyle"/> 
		  <input type="button" name="Submit" value="保存" class="buttonStyle"/> 
		  <input type="button" name="Submit" value="退出" class="buttonStyle"/> 
		</td>
  </tr>
	<tr>
	  <td></td>
  </tr>
</table>
</xsl:template>


<!-- 插入结果列表框 通用模板 -->
<xsl:template name="result_list_iframe">
	<xsl:param name="list_file"/>
	<xsl:param name="height"/>
	<xsl:variable name="src_path" select="concat('./../xml/list_iframe/',$list_file)" /> 

	<iframe name="result_list" align="center" width="95%" marginwidth="0" marginheight="0" frameborder="1" scrolling="yes" > 
		<xsl:attribute name="src"><xsl:value-of select="$src_path"/></xsl:attribute> 
		<xsl:attribute name="height"><xsl:value-of select="$height"/></xsl:attribute> 

			很抱歉，阁下使用的浏览器并不支援 IFrame，不能正常浏览我的网页。
	</iframe>
</xsl:template>


<!-- 插入操作按钮 通用模板 -->
<xsl:template name="op_button_list">
	<xsl:param name="mx"  select="0"/>

	<xsl:param name="clone"  select="0"/>
	<xsl:param name="add"  select="0"/>
	<xsl:param name="del"  select="0"/>
	<xsl:param name="modify"  select="0"/>
	<xsl:param name="check"  select="0"/>
	<xsl:param name="save"  select="0"/>
	<xsl:param name="submit"  select="0"/>
	<xsl:param name="cancle"  select="0"/>
	<xsl:param name="print"  select="0"/>
	<xsl:param name="audit"  select="0"/>
	<xsl:param name="quit"  select="0"/>
	<xsl:param name="reset"  select="0"/>

	<xsl:param name="import" select="0"/>
	<xsl:param name="export" select="1"/>

	<xsl:param name="queue" select="0"/>

<table width="95%" align="center">
	<xsl:if test="$mx=0">
		<xsl:attribute name="class"><xsl:value-of select="opStyle"/></xsl:attribute> 
	</xsl:if>
	<xsl:if test="$mx=1">
		<xsl:attribute name="class"><xsl:value-of select="opStyle_mx"/></xsl:attribute> 
	</xsl:if>

	<tr>
	  <td>
		  <xsl:if test="$clone=1"><input type="button" id="btClone" name="clone" disabled="true" value="克隆" class="buttonStyle" onclick="btClick();" /></xsl:if>
		  <xsl:if test="$clone=1"><xsl:text>&#160;&#160;&#160;&#160;</xsl:text></xsl:if>

			<xsl:if test="$add=1"><input type="button" id="btAdd" name="add" disabled="true" value="增加" class="buttonStyle" onclick="btClick();" /></xsl:if>
		  <xsl:if test="$del=1"><input type="submit" id="btDelete" name="del" disabled="true" value="删除" class="buttonStyle" onclick="btClick();" /></xsl:if>
		  <xsl:if test="$modify=1"><input type="button" id="btModify" name="modify" disabled="true0" value="修改" class="buttonStyle" onclick="btClick();" /></xsl:if>

		  <xsl:if test="$add=1 or $del=1 or $modify=1"><xsl:text>&#160;&#160;&#160;&#160;</xsl:text></xsl:if>

		  <xsl:if test="$check=1"><input type="button" id="btCheck" name="check" disabled="true" value="校验" class="buttonStyle" onclick="btClick();" /></xsl:if> 
		  <xsl:if test="$audit"><input type="button" id="btAudit" name="check"  value="审核" class="buttonStyle" onclick="btClick();" /></xsl:if> 
		  <xsl:if test="$save=1"><input type="button" id="btSave" name="save" disabled="true" value="保存" class="buttonStyle" onclick="btClick();" /></xsl:if>
		  <xsl:if test="$submit=1"><input type="button" id="btSubmit" name="submit" disabled="true" value="提交" class="buttonStyle" onclick="btClick();" /></xsl:if>
		  <xsl:if test="$cancle=1"><input type="button" id="btCancle" name="cancle" disabled="true" value="取消" class="buttonStyle" onclick="btClick();" /></xsl:if>

		  <xsl:if test="$check=1 or $audit=1 or $save=1 or $submit=1 or $cancle=1"><xsl:text>&#160;&#160;&#160;&#160;</xsl:text></xsl:if>

		  <xsl:if test="$print=1"><input type="button" id="btPrint" name="print" disabled="true" value="打印" class="buttonStyle" onclick="btClick();" /></xsl:if>
		  <xsl:if test="$import=1"><input type="button" id="btImport" name="import" disabled="true" value="导入" class="buttonStyle" onclick="btClick();" /></xsl:if>
		  <xsl:if test="$export=1"><input type="button" id="btExport" name="export" disabled="true" value="导出" class="buttonStyle" onclick="btClick();" /></xsl:if>
		  <xsl:if test="$queue=1"><input type="button" id="btQueue" name="queue" disabled="true" value="放入清分队列" class="buttonStyle" onclick="btClick();" /></xsl:if>

		  <xsl:if test="$print=1 or $import=1 or $export=1"><xsl:text>&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;</xsl:text></xsl:if>

		</td>
  </tr>
</table>
</xsl:template>

<!-- 插入操作按钮 通用模板 -->
<xsl:template name="op_button_list_jc">
<table width="95%" align="center" style="position:absolute; top:580;left:30">
	<tr>
	  <td>
		  <input type="button" id="btQueue"  value="打印" class="buttonStyle"  />
		  <input type="button" id="btQueue"  value="导出表格" class="buttonStyle"  />
		</td>
  </tr>
</table>

<table width="100%" align="center" style="position:absolute; top:620">
	<tr>
	  <td>
			<!-- 状态栏 通用模板 -->
			<xsl:call-template name="common_status_table">
			</xsl:call-template>
		</td>
  </tr>
</table>

</xsl:template>





<!-- 表头 通用模板 -->
<xsl:template name="common_table_head">
	<xsl:param name="tbName"/>
	<xsl:param name="tbWidth" select="50"/>

	<table  width="95%" align="center" style="font-size: 9pt;">
		<tr align="center">
			<td align="center"  bgcolor="#D1D1D1" width="{$tbWidth}">	
					<xsl:value-of select="$tbName"/> 
			</td>
			<td>
			</td>
	    <!-- <td colspan="6" bgcolor="#FFFFFF"></td> -->
		</tr>
	</table>
</xsl:template>



<!-- 状态栏 通用模板 -->
<xsl:template name="common_status_table">
	<xsl:param name="mx"  select="0"/>

	<table width="95%" align="center"  class="statusStyle" >
		<xsl:if test="$mx=0">
			<xsl:attribute name="class"><xsl:value-of select="statusStyle"/></xsl:attribute> 
		</xsl:if>
		<xsl:if test="$mx=1">
			<xsl:attribute name="class"><xsl:value-of select="statusStyle_mx"/></xsl:attribute> 
		</xsl:if>

		<tr bgcolor="#D1D1D1">
			<td width="20%" id="promptMessageId" class="status-font">返回信息:<strong><font color="red">
				<xsl:for-each select="/Service/Messages">
				    <xsl:value-of select="Message"/>
				    
				</xsl:for-each>
				
				</font>
				</strong>
			</td>
		</tr>
	</table>
</xsl:template>




<xsl:template name="operateTP">
	<table id="operateTB" style="position:relative; left:20;"> 

	<tr>

		<td>

			<input type="button" name="add" value="增加" onClick="disableAllInputWindow(false);setAction(this);"/>

			<input type="button" name="modify" value="修改" onClick="disableAllInputWindow(false);setAction(this);"/>

			<input type="submit" name="delete" value="删除" onClick="getAllGroupIDs();setAction(this);"/>

      <xsl:text>&#160;&#160;&#160;&#160;&#160;&#160;&#160;</xsl:text>

			<input type="submit" name="save" value="保存"/>
			<input type="button" value="取消" onClick="disableAllInputWindow(true);"/>

			<xsl:text>&#160;&#160;&#160;&#160;&#160;&#160;&#160;</xsl:text>

			<input type="submit" value="打印"/>

			<input type="submit" value="导出"/>

		</td>

	</tr>

</table>
</xsl:template>



<xsl:template name="op_button_list_gen">
	<xsl:param name="mx"  select="0"/>

	<xsl:param name="clone"  select="0"/>
	<xsl:param name="add"  select="0"/>
	<xsl:param name="del"  select="0"/>
	<xsl:param name="modify"  select="0"/>
	<xsl:param name="modify1"  select="0"/>
	<xsl:param name="check"  select="0"/>
	<xsl:param name="save"  select="0"/>
	<xsl:param name="submit"  select="0"/>
	<xsl:param name="detailSubmit"  select="0"/>
	<xsl:param name="cancle"  select="0"/>
	<xsl:param name="print"  select="0"/>
	<xsl:param name="audit"  select="0"/>
	<xsl:param name="quit"  select="0"/>
	<xsl:param name="reset"  select="0"/>

	<xsl:param name="import" select="0"/>
	<xsl:param name="export" select="1"/>

	<xsl:param name="queue" select="0"/>
	<xsl:param name="clickMethod" select="''"/>
	<xsl:param name="distribute" select="0"/>
	<xsl:param name="exClickMethod" select="''"/>
	<xsl:param name="query"  select="0"/>
	<xsl:param name="download"  select="0"/>
	<xsl:param name="statistic"  select="0"/>
	<xsl:param name="import1"  select="0"/>
	<xsl:param name="addClickMethod" select="''"/>
	<xsl:param name="addAfterClickModifyMethod" select="''"/>
	<xsl:param name="addClickMethod1" select="''"/>
	<xsl:param name="addClickMethod2" select="''"/>
	<xsl:param name="addClickMethod3" select="''"/>
	<xsl:param name="addQueryMethod" select="''"/>
	<xsl:param name="addAfterMethod" select="''"/>
	<xsl:param name="addBeforeMethod" select="''"/>
	<xsl:param name="exportBillBefore" select="''"/>
	<xsl:param name="exportBillAfter" select="''"/>
	<xsl:param name="save1"  select="0"/>
	<xsl:param name="check1"  select="0"/>
	<xsl:param name="export1"  select="0"/>
	<xsl:param name="print1"  select="0"/>
	<xsl:param name="print2"  select="0"/>
	<xsl:param name="print3"  select="0"/>
	<xsl:param name="download1"  select="0"/>
	<xsl:param name="refundOk"  select="0"/>
	<xsl:param name="refundNo"  select="0"/>
	<xsl:param name="refundMd"  select="0"/>
	<xsl:param name="refundCk"  select="0"/>
	
	
	<xsl:param name="saveTemp"  select="0"/>
	<xsl:param name="exportBill"  select="0"/>
	
	<xsl:param name="next"  select="0"/>
	<xsl:param name="nextEnd"  select="0"/>
	<xsl:param name="back"  select="0"/>
	<xsl:param name="backEnd"  select="0"/>
	
	
	
	
	




<table width="95%" align="center">
	<xsl:if test="$mx=0">
		<xsl:attribute name="class"><xsl:value-of select="opStyle"/></xsl:attribute> 
	</xsl:if>
	<xsl:if test="$mx=1">
		<xsl:attribute name="class"><xsl:value-of select="opStyle_mx"/></xsl:attribute> 
	</xsl:if>

	<tr>
	  <td>
		  <xsl:if test="$clone=1"><input type="button" id="btClone" name="clone" disabled="true" value="克隆" class="buttonStyle" onclick="{$addQueryMethod};{$clickMethod}" /></xsl:if>
		  <xsl:if test="$clone=1"><xsl:text>&#160;&#160;&#160;&#160;</xsl:text></xsl:if>

			<xsl:if test="$add=1"><input type="button" id="btAdd" name="add" disabled="true" value="增加" class="buttonStyle" onclick="{$addBeforeMethod};{$clickMethod};{$addAfterMethod}" /></xsl:if>
		  <xsl:if test="$del=1"><input type="button" id="btDelete" name="del" disabled="true" value="删除" class="buttonStyle" onclick="{$addQueryMethod};{$clickMethod}" /></xsl:if>
		  <xsl:if test="$modify=1"><input type="button" id="btModify" name="modify" disabled="true0" value="修改" class="buttonStyle" onclick="{$clickMethod};{$addAfterClickModifyMethod}" /></xsl:if>
		  <xsl:if test="$modify1=1"><input type="button" id="btModify1" name="modify1" disabled="true0" value="修改" class="buttonStyle" onclick="{$clickMethod}" /></xsl:if>
			<xsl:if test="$distribute=1"><input type="button" id="btDistribute" name="distribute" disabled="true0" value="分配" class="buttonStyle" onclick="{$exClickMethod}" /></xsl:if>
			
		  <xsl:if test="$add=1 or $del=1 or $modify=1"><xsl:text>&#160;&#160;&#160;&#160;</xsl:text></xsl:if>

		  <xsl:if test="$check=1"><input type="button" id="btCheck" name="check" disabled="true" value="校验" class="buttonStyle" onclick="btClick();" /></xsl:if> 
		  <xsl:if test="$audit"><input type="button" id="btAudit" name="audit"  value="审核" class="buttonStyle" onclick="{$addClickMethod};{$clickMethod};" /></xsl:if> 
		  
		  <xsl:if test="$saveTemp=1"><input type="button" id="btSaveTemp" name="btSaveTemp" disabled="true" value="临存" class="buttonStyle" onclick="{$addQueryMethod};{$addClickMethod};{$clickMethod}" /></xsl:if>
		  <xsl:if test="$save=1"><input type="button" id="btSave" name="save" disabled="true" value="保存" class="buttonStyle" onclick="{$addQueryMethod};{$addClickMethod};{$clickMethod}" /></xsl:if>
		  <xsl:if test="$save1=1"><input type="button" id="btSave1" name="save1" disabled="true" value="保存" class="buttonStyle" onclick="{$addQueryMethod};{$addQueryMethod};{$clickMethod};{$addClickMethod}" /></xsl:if>
		  <xsl:if test="$submit=1"><input type="button" id="btSubmit" name="submit1" disabled="true" value="提交" class="buttonStyle" onclick="{$addQueryMethod};{$clickMethod}" /></xsl:if>
		  <xsl:if test="$detailSubmit=1"><input type="button" id="btDetailSubmit" name="detailSubmit" disabled="true" value="明细提交" class="buttonStyle" onclick="{$addQueryMethod};{$clickMethod}" /></xsl:if>
		  
		  <xsl:if test="$cancle=1"><input type="button" id="btCancle" name="cancle" disabled="true" value="取消" class="buttonStyle" onclick="{$clickMethod}" /></xsl:if>

		  

		  <xsl:if test="$check=1 or $audit=1 or $save=1 or $submit=1 or $cancle=1"><xsl:text>&#160;&#160;&#160;&#160;</xsl:text></xsl:if><xsl:if test="$print=1"><input type="button" id="btPrint" name="print" disabled="true" value="打印" class="buttonStyle" onclick="{$clickMethod}" /></xsl:if>
		  <xsl:if test="$print1=1"><input type="button" id="btPrint1" name="print1" disabled="true" value="打印" class="buttonStyle" onclick="{$clickMethod}" /></xsl:if>
		  <xsl:if test="$print2=1"><input type="button" id="btPrint2" name="print2" disabled="true" value="打印" class="buttonStyle" onclick="{$clickMethod}" /></xsl:if>
		  <xsl:if test="$print3=1"><input type="button" id="btPrint3" name="print3" disabled="true" value="打印" class="buttonStyle" onclick="{$clickMethod}" /></xsl:if>
		  <xsl:if test="$import=1"><input type="button" id="btImport" name="import" disabled="true" value="导入" class="buttonStyle" onclick="{$clickMethod}" /></xsl:if>
		  <xsl:if test="$import1=1"><input type="button" id="btImport1" name="import1" disabled="true" value="导入" class="buttonStyle" onclick="{$clickMethod};{$addClickMethod1}" /></xsl:if>
		  <xsl:if test="$export=1"><input type="button" id="btExport" name="export" disabled="true" value="导出" class="buttonStyle" onclick="{$clickMethod}" /></xsl:if>
		  <xsl:if test="$export1=1"><input type="button" id="btExport1" name="export1" disabled="true" value="导出" class="buttonStyle" onclick="{$clickMethod}" /></xsl:if>
		  <xsl:if test="$queue=1"><input type="button" id="btQueue" name="queue" disabled="true" value="放入清分队列" class="buttonStyle" onclick="btClick();" /></xsl:if>

		  <xsl:if test="$print=1 or $import=1 or $export=1"><xsl:text>&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;</xsl:text></xsl:if>
			<xsl:if test="$query=1"><input type="button" id="btQuery" name="query"  value="查询" class="buttonStyle" onclick="{$addClickMethod};{$clickMethod};" /></xsl:if>
			<xsl:if test="$download=1"><input type="button" id="btDownload" name="download"  value="下发" class="buttonStyle" onclick="{$clickMethod}" /></xsl:if>
			<xsl:if test="$download1=1"><input type="button" id="btDownload1" name="download1"  value="拷盘" class="buttonStyle" onclick="{$clickMethod}" /></xsl:if>
			<xsl:if test="$statistic=1"><input type="button" id="btStatistic" name="statistic"  value="统计" class="buttonStyle" onclick="{$clickMethod}" /></xsl:if>
			<xsl:if test="$check1=1"><input type="button" id="btCheck1" name="check1"  value="盘点" class="buttonStyle" onclick="{$clickMethod};{$addClickMethod}" /></xsl:if>
			<xsl:if test="$refundOk=1"><input type="button" id="btRefundOk" name="btRefundOk"  value="确认退款" class="buttonStyle" onclick="{$addClickMethod};{$clickMethod}" /></xsl:if>
			<xsl:if test="$refundNo=1"><input type="button" id="btRefundNo" name="btRefundNo"  value="拒绝退款" class="buttonStyle" onclick="{$addClickMethod1};{$clickMethod}" /></xsl:if>
			<xsl:if test="$refundMd=1"><input type="button" id="btRefundMd" name="btRefundMd"  value="确认修改" class="buttonStyle" onclick="{$addClickMethod2};{$clickMethod}" /></xsl:if>
			<xsl:if test="$refundCk=1"><input type="button" id="btRefundCk" name="btRefundCk"  value="确认审核" class="buttonStyle" onclick="{$addClickMethod3};{$clickMethod}" /></xsl:if>
		  <xsl:if test="$exportBill=1"><input type="button" id="btExportBill" name="exportBill" disabled="true" value="单据导出" class="buttonStyle" onclick="{$exportBillBefore};{$clickMethod};{$exportBillAfter}" /></xsl:if>
		  
		  <xsl:if test="$backEnd=1"><input type="button" id="btBackEnd" name="btBackEnd" disabled="true" value="始页" class="buttonStyle" onclick="{$clickMethod};" /></xsl:if>
		  <xsl:if test="$back=1"><input type="button" id="btBack" name="btBack" disabled="true" value="前一页" class="buttonStyle" onclick="{$clickMethod};" /></xsl:if>		  
		  <xsl:if test="$next=1"><input type="button" id="btNext" name="btNext" disabled="true" value="下一页" class="buttonStyle" onclick="{$clickMethod};" /></xsl:if>
		  <xsl:if test="$nextEnd=1"><input type="button" id="btNextEnd" name="btNextEnd" disabled="true" value="止页" class="buttonStyle" onclick="{$clickMethod};" /></xsl:if>
			
			
			
		</td>
  </tr>
</table>
</xsl:template>
<xsl:template name="savePriviledges">
	<xsl:for-each select="/Service/Result/ModulePrilivedge">
		<DIV name="modulePriviledges" id="modulePriviledges" value="{concat(queryRight,'#',addRight,'#',deleteRight,'#',modifyRight)}" />
	</xsl:for-each>

	
</xsl:template>

<xsl:template name="saveReportCodeMapping">
	
	<div name="reportCodeMapping" id="reportCodeMapping">
	  <xsl:attribute name="value">
			 <xsl:for-each select="/Service/Result/ReportCodeMappings/pubFlagVO">
			 		<xsl:value-of select="concat(code,'#',codeText,';')"/>
	  	 </xsl:for-each>
		</xsl:attribute>
	</div>
	
</xsl:template>

<xsl:template name="generateYearControl">
  <xsl:param name="selvalue" select="''"/>
	<select name="year">

			<xsl:call-template name="option_list_pub">
					   <xsl:with-param name="root" select="/Service/Result/Years/pubFlagVO"/>
					   <xsl:with-param name="isAll" select="0"/>
					   <xsl:with-param name="selectvalue" select="$selvalue"/>  
			</xsl:call-template>
	</select>
</xsl:template>

<xsl:template name="generateYearControlWithName">
<xsl:param name="submitName" />
	<select name="{$submitName}">
			<xsl:call-template name="option_list_pub">
					   <xsl:with-param name="root" select="/Service/Result/Years/pubFlagVO"/>
					   <xsl:with-param name="isAll" select="0"/>
			</xsl:call-template>
	</select>
</xsl:template>

<xsl:template name="generateYearControlWithNameAll">
<xsl:param name="submitName" />
	<select name="{$submitName}">
			<xsl:call-template name="option_list_pub">
					   <xsl:with-param name="root" select="/Service/Result/Years/pubFlagVO"/>
					   <xsl:with-param name="isAll" select="1"/>
			</xsl:call-template>
	</select>
</xsl:template>


<xsl:template name="generateMonthControl">
	<select name="month">

			 <xsl:call-template name="option_list_pub">
					   <xsl:with-param name="root" select="/Service/Result/Months/pubFlagVO"/>
					   <xsl:with-param name="isAll" select="0"/>
			</xsl:call-template>
	 </select>
</xsl:template>

<!-- hwj add 20090506 -->
<xsl:template name="generateMonthControlWithName">
<xsl:param name="submitName" />
	<select name="{$submitName}">
			<xsl:call-template name="option_list_pub">
					   <xsl:with-param name="root" select="/Service/Result/Months/pubFlagVO"/>
					   <xsl:with-param name="isAll" select="0"/>
			</xsl:call-template>
	</select>
</xsl:template>

<xsl:template name="generateDateControl">	
		<input type="text" size="13" name="date"   require="true"  dataType="Custom" regexp="\d\d\d\d-\d\d-\d\d" msg="日期格式为：年月日如1900-01-02"> 
			<xsl:attribute name="value">
				<xsl:value-of select="/Service/Result/BalanceDate"/>
			</xsl:attribute>
		</input>
				<a href="javascript:openCalenderDialogByID('date','false');">
		  		<img src="./images/calendar.gif" width="15" height="15" border="0" style="block"/>
			  </a>
</xsl:template>
<xsl:template name="generateBalanceDateControl">	
		<input type="text" size="13" name="balanceDate" > 
			<xsl:attribute name="value">
				<xsl:value-of select="/Service/Result/MonthBalanceDate"/>
			</xsl:attribute>
		</input>
				<a  href="javascript:openCalenderDialogByID('balanceDate','false');"> 
		  		<img  src="./images/calendar.gif" width="15" height="15" border="0" style="block" />
			  </a> 
			 
</xsl:template>
<xsl:template name="generateDateControlWithoutCheck">	
		<xsl:param name="relativeRadioIndex" select="-1"/>
		<input type="text" size="13" name="date" > 
				<xsl:attribute name="relRadioIndex">
					<xsl:value-of select="$relativeRadioIndex"/>
				</xsl:attribute>
				<xsl:attribute name="value">
					<xsl:value-of select="/Service/Result/CurrentDate"/>
				</xsl:attribute>
		</input>
				<a href="javascript:openCalenderDialogByID('date','false');">
				
		  		<img src="./images/calendar.gif" width="15" height="15" border="0" style="block"/>
			  </a>
</xsl:template>
<xsl:template name="generateStartDateControl">	
		<input type="text" size="13" name="fromDate" onClick="openCalenderDialogByID('fromDate','false');" require="true"  dataType="LimitB" min="1" msg="请选择起始日期"/> 
				<a href="javascript:openCalenderDialogByID('fromDate','false');">
				
		  		<img src="./images/calendar.gif" width="15" height="15" border="0" style="block"/>
			  </a>
</xsl:template>

<xsl:template name="generateEndDateControl">	
		<input type="text" size="13" name="toDate" onClick="openCalenderDialogByID('toDate','false');" require="true"  dataType="LimitB" min="1" msg="请选择终止日期"/> 
				<a href="javascript:openCalenderDialogByID('toDate','false');">
		  		<img src="./images/calendar.gif" width="15" height="15" border="0" style="block"/>
			  </a>
</xsl:template>




<xsl:template name="generateDayControl">
</xsl:template>



<xsl:template name="generateCardMainTypeControl">
	<xsl:param name="formName"/>
	<xsl:param name="filter_1" select="''"/>
	<xsl:param name="filter_2" select="''"/>
	<xsl:param name="filter_3" select="''"/>
	<xsl:param name="filter_4" select="''"/>
	<xsl:param name="filter_5" select="''"/>
	<select id="mainType" name="mainType" onChange="setSelectValues('{$formName}','mainType','subType','commonVariable');" require="true"  dataType="LimitB" min="1" msg="请选择票卡主类型">				    
			<xsl:call-template name="option_list_pub">
					   <xsl:with-param name="root" select="/Service/Result/CardMainTypes/pubFlagVO"/>
					   <xsl:with-param name="filter_1" select="$filter_1"/>
					   <xsl:with-param name="filter_2" select="$filter_2"/>
					   <xsl:with-param name="filter_3" select="$filter_3"/>
					   <xsl:with-param name="filter_4" select="$filter_4"/>
					   <xsl:with-param name="filter_5" select="$filter_5"/>
			</xsl:call-template>
   </select>
</xsl:template>

<xsl:template name="generateESOperatorControl">
	<xsl:param name="formName"/>
	<select id="ESOperator" name="ESOperator"  require="true"  dataType="LimitB" min="1" msg="请选择ES操作员">				    
			<xsl:call-template name="option_list_pub">
					   <xsl:with-param name="root" select="/Service/Result/ESOperators/pubFlagVO"/>
			</xsl:call-template>
   </select>
</xsl:template>



<xsl:template name="generateCardSubTypeControl" >
  	<xsl:param name="filter_type_all" select="1"/>
		<xsl:param name="filter_type_1" select="''"/>
		<!--主类型ID -->
		<xsl:param name="filter_type_2" select="''"/>
		<xsl:param name="filter_type_3" select="''"/>
		<!--去除的选项I -->
		<xsl:param name="filter_1" select="''"/>
		<xsl:param name="filter_2" select="''"/>
		<xsl:param name="filter_3" select="''"/>
  	<select id="subType" name="subType" require="true"  dataType="LimitB" min="1" msg="请选择票卡子类型">				    
				    <xsl:call-template name="option_list_pub_none">
					    <xsl:with-param name="root" select="/Service/Result/ReportCardSubTypes/pubFlagVO"/>
				    </xsl:call-template>
				    <xsl:call-template name="option_values">
					    <xsl:with-param name="root" select="/Service/Result/ReportCardSubTypes/pubFlagVO"/>
					    <xsl:with-param name="inputName" select="'commonVariable'"/>
					    <xsl:with-param name="filter_type_all" select="$filter_type_all"/>
					    <xsl:with-param name="filter_type_1" select="$filter_type_1"/>
					    <xsl:with-param name="filter_type_2" select="$filter_type_2"/>
					    <xsl:with-param name="filter_type_3" select="$filter_type_3"/>
					    <xsl:with-param name="filter_1" select="$filter_1"/>
					    <xsl:with-param name="filter_2" select="$filter_2"/>
					    <xsl:with-param name="filter_3" select="$filter_3"/>
				    </xsl:call-template>
   </select>
</xsl:template>

<xsl:template name="generateCardSubTypeControlForAll" >
  	<xsl:param name="filter_type_all" select="1"/>
		<xsl:param name="filter_type_1" select="''"/>
		<!--主类型ID -->
		<xsl:param name="filter_type_2" select="''"/>
		<xsl:param name="filter_type_3" select="''"/>
		<!--去除的选项I -->
		<xsl:param name="filter_1" select="''"/>
		<xsl:param name="filter_2" select="''"/>
		<xsl:param name="filter_3" select="''"/>
  	<select id="subType" name="subType" require="true"  dataType="LimitB" min="1" msg="请选择票卡子类型">				    
  	<!--  
				   <xsl:call-template name="option_list_pub_none">
					    <xsl:with-param name="root" select="/Service/Result/CardSubTypes/pubFlagVO"/>
					    
				    </xsl:call-template>
				    -->
				    
				    <xsl:call-template name="option_list_pub_none_all">
					    <xsl:with-param name="root" select="/Service/Result/CardSubTypes/pubFlagVO"/>
					    <xsl:with-param name="defValue" select="99"/>
				    </xsl:call-template>
				    
				    <xsl:call-template name="option_values">
					    <xsl:with-param name="root" select="/Service/Result/CardSubTypes/pubFlagVO"/>
					    <xsl:with-param name="inputName" select="'commonVariable'"/>
					    <xsl:with-param name="filter_type_all" select="$filter_type_all"/>
					    <xsl:with-param name="filter_type_1" select="$filter_type_1"/>
					    <xsl:with-param name="filter_type_2" select="$filter_type_2"/>
					    <xsl:with-param name="filter_type_3" select="$filter_type_3"/>
					    <xsl:with-param name="filter_1" select="$filter_1"/>
					    <xsl:with-param name="filter_2" select="$filter_2"/>
					    <xsl:with-param name="filter_3" select="$filter_3"/>
				    </xsl:call-template>
				    
				     
   </select>
</xsl:template>

<!--
<xsl:template name="generateLineControl">
  <xsl:param name="isOnChange" select="1"/>
	<select id="lineID" name="lineID" >				    
			<xsl:call-template name="option_list_pub">
					<xsl:with-param name="root" select="/Service/Result/Lines/pubFlagVO"/>
			</xsl:call-template>
   </select>
</xsl:template>
-->

<xsl:template name="generateLineControl" >
	<xsl:param name="formName"/>	
	<xsl:param name="isOnChange" select="1"/>
	<select id="lineID" name="lineID" require="true"  dataType="LimitB" min="1" msg="请选择线路">
		<xsl:if test="$isOnChange=1">
			<xsl:attribute name="onChange" >
				setSelectValues('<xsl:value-of select="$formName"/>','lineID','stationID','commonVariable1');
			</xsl:attribute>		
		</xsl:if>
			<xsl:call-template name="option_list_pub">
					<xsl:with-param name="root" select="/Service/Result/Lines/pubFlagVO"/>
		   </xsl:call-template>
  </select>
</xsl:template>

<xsl:template name="generateStationControl">
   <select id="stationID" name="stationID" require="true"  dataType="LimitB" min="1" msg="请选择车站">
			<xsl:call-template name="option_list_pub_none">
			   <xsl:with-param name="root" select="/Service/Result/Stations/pubFlagVO"/>
			</xsl:call-template>
				    
		  <xsl:call-template name="option_values">
					<xsl:with-param name="root" select="/Service/Result/Stations/pubFlagVO"/>
					<xsl:with-param name="inputName" select="'commonVariable1'"/>
		 </xsl:call-template>
	 </select>
</xsl:template>

<xsl:template name="generateReport">
	<xsl:param name="note" select="''"/>
	<table id="reportList" class="tableStyle" width="95%"  align="center" >
	
	<xsl:attribute name="style">
	  display:<xsl:value-of select="/Service/Result/Display"/>
	</xsl:attribute>
	
		<tr align="center" class="trTitle">
				<td width="15%" height="18"><div align="center" style="font-size:9pt">序号</div></td>
				<td width="40%"><div align="center" style="font-size:9pt">报表连接</div></td>
				<td width="15%"><div align="center" style="font-size:9pt">清算日期</div></td>
				<td><div align="center" style="font-size:9pt">备注</div></td>
  	</tr>
  	<xsl:for-each select="/Service/Result/ReportNames/reportAttribute">
  	   <tr class="trContent">
				<td width="15%" height="18"><div align="center" style="font-size:9pt">
					<xsl:number />
				</div></td>
				<td width="40%"><div align="center" style="font-size:9pt"><a href="{reportURL}"  target="_blank"><xsl:value-of select="operationDate"/></a></div></td>
				<td width="15%"><div align="center" style="font-size:9pt"><xsl:value-of select="balanceDate" /></div></td>
				<td><div align="center" style="font-size:9pt">
					<xsl:value-of select="$note"/> 
				</div></td>
		 </tr>		
  	</xsl:for-each>
  	

</table>
</xsl:template>

<xsl:template name="generateReportButton">
 <xsl:param name="formName" select="''"/>
 <xsl:param name="reportType" select="''"/>
 <xsl:param name="controlNames" select="''"/>
 <input type="button" id="btReportQuery" name="ReportQuery"  value="查询" class="buttonStyle" >
 	<xsl:attribute name="onclick">
  	btnClickForReport('<xsl:value-of select="$formName"/>','<xsl:value-of select="$reportType"/>','<xsl:value-of select="$controlNames"/>')
  </xsl:attribute>
 </input>
</xsl:template>

<xsl:template name="generateDecisionReportButton">
 <xsl:param name="formName" select="''"/>
 <xsl:param name="controlNames" select="''"/>
 <input type="button" id="btStatistic" name="statistic"  value="统计" class="buttonStyle" >
 	<xsl:attribute name="onclick">
  	btnClickForDecisionReport('<xsl:value-of select="$formName"/>','<xsl:value-of select="$controlNames"/>')
  </xsl:attribute>
 </input>
</xsl:template>
<!--
<xsl:template name="generateCommonVersionParam">
  <xsl:param name="curent_version_start_date" select="/Service/Result/CurrentVersionInformation/b_ava_time"/>
  <DIV  name="curent_version_start_date_value" id="curent_version_start_date_value" value="{$curent_version_start_date}" />
</xsl:template>
-->
<xsl:template name="generateVersionParam">
	<input type="hidden" name="Version">
			<xsl:attribute name="value">
				<xsl:value-of select="/Service/Result/Version"/>
			</xsl:attribute>
	</input>
	<input type="hidden" name="VersionType">
			<xsl:attribute name="value">
				<xsl:value-of select="/Service/Result/VersionType"/>
			</xsl:attribute>
	</input>
	<input type="hidden" name="Type">
			<xsl:attribute name="value">
				<xsl:value-of select="/Service/Result/Type"/>
			</xsl:attribute>
	</input>
	<input type="hidden" name="StartDate">
			<xsl:attribute name="value">
				<xsl:value-of select="/Service/Result/StartDate"/>
			</xsl:attribute>
	</input>
	<input type="hidden" name="EndDate">
			<xsl:attribute name="value">
				<xsl:value-of select="/Service/Result/EndDate"/>
			</xsl:attribute>
	</input>
</xsl:template>
<xsl:template name="generateScreenParam">
   <input type="hidden" name="lineID" value="{/Service/Result/LineID}"/>
	 <input type="hidden" name="stationID" value="{/Service/Result/StationID}"/>
   <input type="hidden" name="clickedNodeID" value="{/Service/Result/ClickedNodeID}"/>
   <input type="hidden" name="EnglishVersion" value="{/Service/Result/EnglishVersion}"/>
   <input type="hidden" name="screenOperationType" value="{/Service/Result/ScreenOperationType}"/>
   <input type="hidden" name="command" value="{/Service/Result/Command}"/>
</xsl:template>
<xsl:template name="generate_report_is_balanceDate">
	<xsl:param name="isCheck" select="0"/>
		<input type="checkbox" name="isBalanceDate" value="0" >
			<xsl:if test="$isCheck=1">
			   <xsl:attribute name="checked">
			   		true
			   </xsl:attribute>
			</xsl:if>
		</input>
				按清算日				
</xsl:template>
<xsl:template name="generate_month_report_is_balanceDate">
	<xsl:param name="isCheck" select="0"/>
		<input type="checkbox" name="isBalanceDate" value="0" >
			<xsl:if test="$isCheck=1">
			   <xsl:attribute name="checked">
			   		true
			   </xsl:attribute>
			</xsl:if>
		</input>
				按清算日	
		
</xsl:template>

<!--//新增  -->

<xsl:template name="option_sub_values">
	<xsl:param name="root"/>
	<xsl:param name="inputName"/>
	<!--1：去除所有主类型的至多3个固定选项 0：去至多3主类型的某一个固定选项  -->
	<xsl:param name="filter_type_all" select="1"/>
	<xsl:param name="filter_type_1" select="''"/>
	<!--主类型ID -->
	<xsl:param name="filter_type_2" select="''"/>
	<xsl:param name="filter_type_3" select="''"/>
	<!--去除的选项I -->
	<xsl:param name="filter_1" select="''"/>
	<xsl:param name="filter_2" select="''"/>
	<xsl:param name="filter_3" select="''"/>
		<input type="hidden" id="{$inputName}" name="{$inputName}">
		  	<xsl:attribute name="value">
		  		<xsl:for-each select="$root">
		  			<!-- 
		  			<xsl:if test="(($filter_type_all=1) and (not(code =$filter_1))and (not(code =$filter_2)) and (not(code =$filter_3)))or (($filter_type_all=0)and not(strType=$filter_type_1) and not(strType=$filter_type_2) and not(strType=$filter_type_3)) or (($filter_type_all=0)and (strType=$filter_type_1) and not(code=$filter_1)) or(($filter_type_all=0)and (strType=$filter_type_2) and not(code=$filter_2)) or(($filter_type_all=0)and (strType=$filter_type_3) and not(code=$filter_3))">  
		  				<xsl:value-of select="concat(strType,maincode,',',subcode,',',preexamine_cnt_lmt,',',is_preexamine,'exp_date',',',deposit_amnt,':')" />
		  			</xsl:if>
		  			 -->
		  			<xsl:value-of select="concat(strType,maincode,',',subcode,',',cardmaxmon,',',flagmon,',',period,',',deposit,',',maxrecharge,':')" />

		  		</xsl:for-each>
		    </xsl:attribute>
		</input>
</xsl:template>


</xsl:stylesheet>