<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<acme:form readonly="${readOnly}"> 
	<acme:input-textbox code="inventor.artifact.form.label.name" path="name"/>
	<acme:input-textbox code="inventor.artifact.form.label.code" path="code"/>
	<acme:input-textbox code="inventor.artifact.form.label.technology" path="technology"/>
	<acme:input-textarea code="inventor.artifact.form.label.description" path="description"/>
	<acme:input-money code="inventor.artifact.form.label.retailprice" path="retailPrice"/>
	<acme:input-textbox code="inventor.artifact.form.label.link" path="link"/>
	
	<jstl:choose>
	 
		<jstl:when test="${acme:anyOf(command, 'show, update, delete, publish') && published == false}">
			<jstl:if test="${artifactType.toString() == 'TOOL'}">
				<acme:input-select code="inventor.artifact.list.label.plaba" path="plaba">
					<acme:input-option code="inventor.artifact.form.option.none" value="-1"/>
					<jstl:forEach items="${plabas}" var="optionPlaba">
						<acme:input-option code="${optionPlaba.getPattern()}" value="${optionPlaba.id}" selected="${plaba.equals(optionPlaba)}"/>
					</jstl:forEach>
				</acme:input-select>
			</jstl:if>

			<acme:input-textbox code="inventor.artifact.list.label.type" path="artifactType" readonly="true"/>
			<acme:submit code="inventor.artifact.form.button.update" action="/inventor/artifact/update"/>
			<acme:submit code="inventor.artifact.form.button.delete" action="/inventor/artifact/delete"/>
			<acme:submit code="inventor.artifact.form.button.publish" action="/inventor/artifact/publish"/>
		</jstl:when>
		
		<jstl:when test="${command == 'create'}">
			<jstl:if test="${type.toString() == 'TOOL'}">
				<acme:input-select code="inventor.artifact.list.label.plaba" path="plaba">
					<acme:input-option code="inventor.artifact.form.option.none" value="-1"/>
					<jstl:forEach items="${plabas}" var="optionPlaba">
						<acme:input-option code="${optionPlaba.getPattern()}" value="${optionPlaba.id}"/>
					</jstl:forEach>
				</acme:input-select>
			</jstl:if>
			
			<acme:input-textbox code="inventor.artifact.list.label.type" path="type" readonly="true"/>
			<acme:submit code="inventor.artifact.form.button.create" action="/inventor/artifact/create"/>
		</jstl:when>
				
		<jstl:when test="${command == 'show' && published == true }">
			<acme:input-textbox code="inventor.artifact.list.label.plaba" path="plaba.code"/>
			<acme:input-textbox code="inventor.artifact.list.label.type" path="artifactType"/>
		</jstl:when>
	</jstl:choose>
</acme:form>