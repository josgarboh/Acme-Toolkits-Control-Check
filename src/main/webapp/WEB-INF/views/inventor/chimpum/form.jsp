<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<acme:form readonly="${readOnly}">
	<acme:input-textbox code="inventor.chimpum.form.label.code" path="code"/>
	<acme:input-moment code="inventor.chimpum.form.label.creation-moment" path="creationMoment"/>
	<acme:input-textbox code="inventor.chimpum.form.label.title" path="title"/>
	<acme:input-textbox code="inventor.chimpum.form.label.description" path="description"/>
	<acme:input-moment code="inventor.chimpum.form.label.start-date" path="startDate"/>
	<acme:input-moment code="inventor.chimpum.form.label.finish-date" path="finishDate"/>
	<acme:input-money code="inventor.chimpum.form.label.budget" path="budget"/>
	<acme:input-textbox code="inventor.chimpum.form.label.link" path="link"/>
	
	
	<jstl:choose>
		<jstl:when test="${command == 'create'}">		
			<acme:submit code="inventor.chimpum.form.button.create" action="/inventor/chimpum/create"/>			
		</jstl:when>
	</jstl:choose>	
	
	
	<acme:button code="inventor.chimpum.form.button.artifact" action="/inventor/artifact/list-chimpum?chimpumId=${chimpumId}"/>
</acme:form>

