<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<acme:list>
	<acme:list-column code="inventor.chimpum.form.label.code" path="code"/>
	<acme:list-column code="inventor.chimpum.form.label.creation-moment" path="creationMoment"/>
	<acme:list-column code="inventor.chimpum.form.label.title" path="title"/>
</acme:list>

<acme:button code="inventor.chimpum.list.button.create" action="/inventor/chimpum/create"/>