pipeline {
    agent none
    environment{
       APP_NAME = "storage-service"
       TAG = "latest"
       IMAGE_TAG = "us-central1-docker.pkg.dev/experiment-ifg-433204/ifg-docker-registry/${APP_NAME}:${TAG}"
       NAMESPACE = "web-apps"
       ACCOUNT_IMPERSONATED = "aldev-tech-gke@experiment-ifg-433204.iam.gserviceaccount.com"
       CLUSTER = "aldev-tech"
       ZONE = "us-central1-c"
       PROJECT_ID = "experiment-ifg-433204"
    }
    stages {
        stage('init kubernetes configuration'){
            agent any
            sh '''
                gcloud container clusters get-credentials ${CLUSTER} --zone ${ZONE} --project ${PROJECT_ID} --impersonate-service-account=${ACCOUNT_IMPERSONATED}
            '''
        }
        stage('build') {
            agent any
            sh '''
                mvn clean package
            '''
        }

        stage('Deploy Image'){
            agent any
            sh '''
                =======================DOCKER STEP==========================
                docker build -f src/main/docker/Dockerfile.jvm -t ${APP_NAME}:${TAG} .
                docker tag ${APP_NAME}:${TAG} ${IMAGE_TAG}
                docker push ${IMAGE_TAG}
            '''
        }

        stage('Deploy Service'){
            agent any
            sh '''
                =======================KUBERNETES STEP========================
                kubectl apply -f storage-service.yaml -n ${NAMESPACE}
            '''
        }
    }
}
