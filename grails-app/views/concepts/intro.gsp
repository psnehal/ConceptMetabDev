<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'concepts.label', default: 'Concepts')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
		 <link rel="stylesheet" href="http://code.jquery.com/ui/1.10.0/themes/base/jquery-ui.css" />
		 <script src="http://code.jquery.com/jquery-1.8.3.js"></script>
        <script src="http://code.jquery.com/ui/1.10.0/jquery-ui.js"></script> 
        
        <g:javascript>
            $(document).ready(function() {
               $('#city').autocomplete({
                      source: '<g:createLink controller="Concepts" action="ajaxFindCity"/>'
                  
                });
              
            });       
        </g:javascript>
	</head>
	<body>
<div id="list-concept_types" class="content scaffold-list" role="main">	
	
			<center><h1>Annotation Sources in ConceptMetab</h1></center>
		
		<div id="paraPanel">
		<h3>KEGG Pathways</h3>
		We considered 85 human metabolic pathways from the summer 2011 freeze of 
		KEGG (1), and parsed the corresponding XML files to directly associate 
		compounds with pathways. Taking only those pathways with 5 or more compounds 
		(a restriction used for all annotation sources), we obtained 74 KEGG Pathways. 
		</div>
		
		
	
		<div id="paraPanel">
		<h3>Gene Ontology</h3>
		We used version 2.9.0 of the org.Hs.eg.db R package (2) to map Gene Ontology 
		terms to Entrez IDs for genes, and version 2.9.0 of the GO.db R package (3) 
		to annotate GO IDs to their names. Next, we used information parsed from KEGG 
		Pathway XML files to map genes to reactions and reactions to compounds. We
		 split the GO terms into their three top-level categories: GO Biological 
		 Process (GOBP) having 3,385 compound sets, GO Cellular Component (GOCC) having
		  300 compound sets, and GO Molecular Function (GOMF) having 839 compound sets.
		   We observed 5 GO terms to be supersets of all other GO terms in their 
		   respective top-level categories – metabolic process and organic substance
		    metabolic process in GOBP, cell and cell part in GOCC, and catalytic 
		    activity in GOMF – and have removed them.
		 </div>   
		
		
		<div id="paraPanel">
		<h3>Enzymes</h3>
		We used the org.Hs.eg.db R package (2) to map enzymes to genes. We then reused the gene to reaction to compound mapping from KEGG. There are 176 compound sets relating enzymes to compounds.
		</div>
		
		<div id="paraPanel">
		<h3>Medical Subject Headings</h3>
		We leveraged the database developed for Metab2MeSH (4), which uses Fisher's Exact Test to annotate PubChem compounds to concepts defined in MeSH, the National Library of Medicine's controlled vocabulary for biology and medicine used to manually index articles for MEDLINE/PubMed. We elected to consider the top-level MeSH categories for Anatomy, Diseases, Organisms, Phenomena and Processes, Psychiatry and Psychology, and Agriculture and Technology. MeSH has a tree structure, and a MeSH term may appear in different branches of the tree. To remove the ambiguity of which top-level category a term should be considered a part of, we prioritized membership thus: Diseases, Phenomena and Process, Psychiatry and Psychology, Anatomy, Organisms, Technology and Agriculture. See the table below for the number of concepts in each top-level MeSH category.
		</div>
		
		<div id="paraPanel">
		<h3>Table: Summary of concept types, their sizes, mean concept sizes, and background set sizes.</h3>
		<table>
				<tr>
				<th>Concept Type</th>
				<th># Concepts</th>
				<th>Mean Concept Size</th>
				<th># Compounds</th>
				</tr>
				<tr>
				<td>Enzyme</td>
				<td>175</td>
				<td>11</td>
				<td>874</td>
				</tr>
				<tr>
				<td>GO Biological Process </td>
				<td>3712</td>
				<td>56</td>
				<td>1220</td>
				</tr><tr>
				<td>GO Cellular Component</td>
				<td>346</td>
				<td>117</td>
				<td>1213</td>
				</tr><tr>
				<td>GO Molecular Function</td>
				<td>864</td>
				<td>48</td>
				<td>1226</td>
				</tr><tr>
				<td>KEGG Pathway</td>
				<td>74</td>
				<td>42</td>
				<td>2427</td>
				</tr>
				<tr>
				<td>MeSH Anatomy</td>
				<td>1506</td>
				<td>357</td>
				<td>37706</td>
				</tr>
				<tr>
				<td>MeSH Diseases</td>
				<td>4089</td>
				<td>182</td>
				<td>33074</td>
				</tr>
					<tr>
				<td>MeSH Organisms</td>
				<td>3011</td>
				<td>150</td>
				<td>48688</td>
				</tr>
					<tr>
				<td>MeSH Phenomena and Processes</td>
				<td>1443</td>
				<td>404</td>
				<td>43016</td>
				</tr>
					<tr>
				<td>MeSH Psychiatry and Psychology</td>
				<td>519</td>
				<td>180</td>
				<td>9188</td>
				</tr>
					<tr>
				<td>MeSH Technology, Industry, and Agriculture</td>
				<td>330</td>
				<td>280</td>
				<td>15721</td>
				</tr>
		
		</table>
		</div>
		<div id="paraPanel">
		<h3>References</h3>
		<ol>
		<li>Kanehisa,M. et al. (2011) KEGG for integration and interpretation of large-scale molecular data sets. Nucleic Acids Research, 40, D109–D114.</li>
	    <li>Carlson M. org.Hs.eg.db: Genome wide annotation for Human. R package version 2.9.0.</li>
		<li>Carlson M. GO.db: A set of annotation maps describing the entire Gene Ontology. R package version 2.9.0.</li>
		<li>Sartor,M.A. et al. (2012) Metab2MeSH: annotating compounds with medical subject headings. Bioinformatics, 28, 1408–1410.</li>
		</ol>
		</div>
</div>
</body>
</html>