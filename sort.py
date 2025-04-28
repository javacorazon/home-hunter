from typing import List, Dict

class PropertySorter:
    @staticmethod
    def sort_by_price(properties: List[Dict], ascending: bool = True) -> List[Dict]:
        return sorted(properties, key=lambda x: x.get("price", 0), reverse=not ascending)

    @staticmethod
    def sort_by_date(properties: List[Dict], ascending: bool = True) -> List[Dict]:
        return sorted(properties, key=lambda x: x.get("created_at", ""), reverse=not ascending)

    @staticmethod
    def sort_by_rating(properties: List[Dict], ascending: bool = True) -> List[Dict]:
        return sorted(properties, key=lambda x: x.get("rating", 0), reverse=not ascending)