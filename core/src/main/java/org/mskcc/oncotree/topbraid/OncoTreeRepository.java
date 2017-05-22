/*
 * Copyright (c) 2017 Memorial Sloan-Kettering Cancer Center.
 *
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY, WITHOUT EVEN THE IMPLIED WARRANTY OF
 * MERCHANTABILITY OR FITNESS FOR A PARTICULAR PURPOSE.  The software and
 * documentation provided hereunder is on an "as is" basis, and
 * Memorial Sloan-Kettering Cancer Center
 * has no obligations to provide maintenance, support,
 * updates, enhancements or modifications.  In no event shall
 * Memorial Sloan-Kettering Cancer Center
 * be liable to any party for direct, indirect, special,
 * incidental or consequential damages, including lost profits, arising
 * out of the use of this software and its documentation, even if
 * Memorial Sloan-Kettering Cancer Center
 * has been advised of the possibility of such damage.
*/

package org.mskcc.oncotree.topbraid;

import java.util.List;

import org.apache.log4j.Logger;

import org.mskcc.oncotree.model.Version;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Manda Wilson
 **/
@Repository
public class OncoTreeRepository extends TopBraidRepository<OncoTreeNode> {

    private final static Logger logger = Logger.getLogger(OncoTreeRepository.class);

    private String query = "PREFIX skos:<http://www.w3.org/2004/02/skos/core#> " +
        "PREFIX onc:<http://data.mskcc.org/ontologies/oncotree/> " +
        "SELECT DISTINCT ?code ?name ?mainType ?color ?nci ?umls ?icdo ?parentCode " +
        "WHERE { " +
        "   GRAPH <%s> { " +
        "       ?s skos:prefLabel ?name;" +
        "       skos:broader ?broader;" +
        "       skos:notation ?code." +
        "       OPTIONAL{?broader skos:notation ?parentCode}." +
        "       OPTIONAL{?s onc:mainType ?mainType}." +
        "       OPTIONAL{?s onc:color ?color}." +
        "       OPTIONAL{?s onc:nci ?nci}." +
        "       OPTIONAL{?s onc:umls ?umls}." +
        "       OPTIONAL{?s onc:icdo ?icdo}." +
        "   }" +
        "}";

    public List<OncoTreeNode> getOncoTree(Version version) throws TopBraidException {
        return super.query(String.format(query, version.getGraphURI()), new ParameterizedTypeReference<List<OncoTreeNode>>(){});
    }

}
