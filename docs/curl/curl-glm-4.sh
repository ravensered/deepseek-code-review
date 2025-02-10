curl -X POST \
        -H "Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiIsInNpZ25fdHlwZSI6IlNJR04ifQ.eyJhcGlfa2V5IjoiODdlMjgwMWVmOGFiNGI5OGJkOGZlYmE2MjY2ZDE4MTgiLCJleHAiOjE3MzkxOTIyNjM5MjcsInRpbWVzdGFtcCI6MTczOTE5MDQ2MzkzM30.4o5Jjyh8rrRNIkZo3pquaq4hWaf5koZndgSkO8sgBuo" \
        -H "Content-Type: application/json" \
        -H "User-Agent: Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)" \
        -d '{
          "model":"glm-4",
          "stream": "true",
          "messages": [
              {
                  "role": "user",
                  "content": "1+1"
              }
          ]
        }' \
  https://open.bigmodel.cn/api/paas/v4/chat/completions
