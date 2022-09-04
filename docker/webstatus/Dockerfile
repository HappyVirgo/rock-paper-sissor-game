FROM mcr.microsoft.com/dotnet/aspnet:6.0 AS base

# Set working directory
WORKDIR /app
EXPOSE 80

FROM mcr.microsoft.com/dotnet/sdk:6.0 AS build

# It's important to keep lines from here down to "COPY . ." identical in all Dockerfiles
# to take advantage of Docker's build cache, to speed up local container builds
COPY ./web/WebStatus/WebStatus.csproj /web/WebStatus/WebStatus.csproj
COPY ./docker-compose.dcproj /docker-compose.dcproj
COPY ./NuGet.config /NuGet.config
COPY ./web/WebStatus /web/WebStatus

# Set working directory
WORKDIR /web/WebStatus

RUN dotnet publish -c Release -o /app

FROM build AS publish

FROM base AS final

# Set working directory
WORKDIR /app

COPY --from=publish /app .

# Webstatus app init
ENTRYPOINT ["dotnet", "WebStatus.dll"]
