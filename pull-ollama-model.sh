#!/bin/bash

# Wait for Ollama to be ready
echo "Waiting for Ollama to be ready..."
until curl -s http://localhost:11434/api/tags > /dev/null; do
  sleep 1
done

# Pull the llama3.2 model
echo "Pulling llama3.2 model..."
docker exec stock-assistant-ollama ollama pull llama3.2

echo "Model pull complete!" 