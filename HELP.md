{"settings": {"index": {"number_of_shards": "5","number_of_replicas": "1"}},"mappings": {"test_region": {"properties": {"id": {"type": "long"},"name": {"type": "keyword"},"location": {"type": "geo_point"}}}}}

{"name": "beijing", "id": "1", "location": {"lat": 39.54, "lon": 116.28} }

{"name": "shanghai", "id": "2", "location": {"lat": 31.12, "lon": 121.26} }

{"name": "guangzhou", "id": "3", "location": {"lat": 23.08, "lon": 113.14} }