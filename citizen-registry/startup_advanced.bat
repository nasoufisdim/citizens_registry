docker network create citizen-net
docker run -d --name mysql --network citizen-net --network-alias mysql -v mysql:/var/lib/mysql -e MYSQL_RANDOM_ROOT_PASSWORD=yes -e MYSQL_USER=citizen -e MYSQL_PASSWORD=citizen!25$ -e MYSQL_DATABASE=citizens mysql:8.3.0

@echo off
:retry
echo Waiting for MySQL to become available...
timeout /t 1 >nul
docker exec -it mysql mysql -ucitizen -pcitizen!25$ -e "SELECT 1" >nul 2>&1
if %errorlevel% neq 0 (
    goto retry
)
echo MySQL is up!

docker run -d --name citizen --network citizen-net -p 8080:8080 -e DB_HOST=mysql -e DB_USER=citizen -e DB_PASSWORD=citizen!25$ -e DB_NAME=citizens citizen:0.1