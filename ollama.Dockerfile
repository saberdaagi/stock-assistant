FROM ollama/ollama:latest

# Install bash and curl
RUN apt-get update && \
    apt-get install -y --no-install-recommends \
    curl \
    && rm -rf /var/lib/apt/lists/*

# Copy the initialization script
COPY init-ollama.sh /init-ollama.sh
RUN chmod +x /init-ollama.sh

# Set the entrypoint to our script
ENTRYPOINT ["/init-ollama.sh"] 