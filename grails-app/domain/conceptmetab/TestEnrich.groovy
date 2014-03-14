package conceptmetab

class TestEnrich {

static mapping = {
table 'enrichments'
columns{
//d column: 'enrich_id'
id1 column: 'id1_id'
id2 column: 'id2_id'
intersection column: 'intersection'
pval column: 'pval'
qval column: 'qval'
}
}

	
	
Integer id1
Integer id2
Integer intersection
Double pval
Double qval
	
}
