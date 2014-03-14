<div id="searchm">
  <g:form url='[controller: "concepts", action: "search"]'
          id="searchableForm"
          name="searchableForm"
          method="get">
    <g:textField name="q" value="${params.q}" />
    <input type="submit" value="Search" />
  </g:form>
</div>