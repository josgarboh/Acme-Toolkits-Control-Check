<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<acme:form>
	<acme:input-textbox readonly="${acme:anyOf(command, 'show, update, delete')}" code="inventor.plaba.form.label.code" placeholder="012345" path="code"/>
	<acme:input-moment readonly="true" code="inventor.plaba.form.label.creation-moment" path="creationMoment"/>
	<acme:input-textbox code="inventor.plaba.form.label.subject" path="subject"/>
	<acme:input-textarea code="inventor.plaba.form.label.summary" path="summary"/>
	<acme:input-moment code="inventor.plaba.form.label.start-date" path="startDate"/>
	<acme:input-moment code="inventor.plaba.form.label.finish-date" path="finishDate"/>
	<acme:input-money code="inventor.plaba.form.label.income" path="income"/>
	<acme:input-textbox code="inventor.plaba.form.label.more-info" path="moreInfo"/>
	
	
	<jstl:choose>
		<jstl:when test="${command == 'create'}">		
			<acme:submit code="inventor.plaba.form.button.create" action="/inventor/plaba/create"/>			
		</jstl:when>
		
		<jstl:when test="${acme:anyOf(command, 'show, update, delete')}">
			<acme:button code="inventor.plaba.form.button.artifact" action="/any/artifact/list-with-plaba?plabaId=${plabaId}"/>
			<acme:submit code="inventor.plaba.form.button.update" action="/inventor/plaba/update"/>
			<acme:submit code="inventor.plaba.form.button.delete" action="/inventor/plaba/delete"/>
		</jstl:when>
	</jstl:choose>	

</acme:form>