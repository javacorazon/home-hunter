from elasticsearch import Elasticsearch
from typing import List, Dict

class PropertySearcher:
    def __init__(self):
        self.es = Elasticsearch(['http://localhost:9200'])
        self.index_name = "properties"

    def search_properties(self, query: str, filters: Dict = None) -> List[Dict]:
        """
        Search properties based on query and filters
        """
        search_body = {
            "query": {
                "bool": {
                    "must": {
                        "multi_match": {
                            "query": query,
                            "fields": ["title", "description", "location"]
                        }
                    }
                }
            }
        }

        if filters:
            filter_clauses = []
            for key, value in filters.items():
                if key == "price_range":
                    filter_clauses.append({
                        "range": {
                            "price": {
                                "gte": value[0],
                                "lte": value[1]
                            }
                        }
                    })
                else:
                    filter_clauses.append({
                        "term": {
                            f"{key}.keyword": value
                        }
                    })

            search_body["query"]["bool"]["filter"] = filter_clauses

        response = self.es.search(index=self.index_name, body=search_body)
        return [hit["_source"] for hit in response["hits"]["hits"]]

    def suggest_properties(self, prefix: str) -> List[str]:
        """
        Suggest properties based on prefix
        """
        response = self.es.search(
            index=self.index_name,
            body={
                "suggest": {
                    "property-suggest": {
                        "prefix": prefix,
                        "completion": {
                            "field": "title_suggest"
                        }
                    }
                }
            }
        )
        return [option["text"] for option in response["suggest"]["property-suggest"][0]["options"]]