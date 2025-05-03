#!/bin/sh
echo "Starting Ollama server..."
ollama serve &
sleep 10
echo "Pulling llama3.2 model..."
ollama pull llama3.2
wait 