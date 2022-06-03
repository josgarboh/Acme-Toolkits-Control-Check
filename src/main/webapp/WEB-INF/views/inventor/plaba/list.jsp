<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<acme:list>
	<acme:list-column code="inventor.plaba.form.label.code" path="code"/>
	<acme:list-column code="inventor.plaba.form.label.creation-moment" path="creationMoment"/>
	<acme:list-column code="inventor.plaba.form.label.title" path="title"/>
</acme:list>

<acme:button code="inventor.plaba.list.button.create" action="/inventor/plaba/create"/>