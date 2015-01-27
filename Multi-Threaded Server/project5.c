/////////////////////////////////////////////
///////NAME:Shimpei Kurokawa
///////TITLE:Basic multi-threaded server implemented in C.
///////DESCRIPTION:
///////      A Simple server implemented in C. Utilizes basic
///////      HTTP protocols along with POSIX Threads.
///////      Also writes the request to stats.txt and
///////      implements mutex lock to prevent data races.
///////NOTE:
///////      Must have requested html file in current directory.
///////
/////////////////////////////////////////////


////////////////////////
//Included Libary
////////////////////////
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <pthread.h>
#include <netinet/ip.h>

////////////////////////
//Worker thread Function
////////////////////////
 void *threadconnect(void *p){

     //declare variables
    int buffersize=1024;
    char buffer[buffersize];
    char input[buffersize];
    char filename[100];
    int *connfd=(int *)p;
	pthread_mutex_t mut = PTHREAD_MUTEX_INITIALIZER;

    fprintf(stderr,"Message Sent\n");

    //get response
    int size=0;
    int adress;
    int tran;
    int clcount=0;
    int i;
    //Copy response to buffer until no more data is received
    while(1){

    tran=recv(*connfd, input+size, buffersize-size,0);
    size=size+tran;
    for(i=0;i<size;i++){
        if (input[i]==13&&input[i+1]==10){
            clcount++;
        }
    }
    if (clcount>=2){
        break;
    }

    }
    //check format of request
    char format1=0;
    char format2=0;
    char str1[10];
    char str2[10];
    int ret;
    strcpy(str1,"GET /");
    strcpy(str2,"Host: ");

    for(i=0;i<size;i++){

         ret = strncmp(str1, input+i, 5);
         if(ret==0){
            format1=1;
         }
         ret = strncmp(str1, input+i,6 );
            if(ret=0){
            format2=1;
         }
    }

    fprintf(stderr,"Tran: %d\n",tran);

    //if only one null line ask for second line
    fprintf(stderr,"\nThe size of input is : %d \n" , size);
    fprintf(stderr,"The message is :\n%s", input);
    fprintf(stderr,"\nMessage Recieved\n");

    //Parse file name from request
    int k=0;
    for(i=5;input[i]!=32;i=i+1){
        if(i>size-1){
            break;
        }
        filename[k]=input[i];
        k++;
    }
    filename[k]=0;


    fprintf(stderr,"File Name is : %s\n", filename);
    FILE *fp;
    fp=fopen(filename,"r");

    //Validate request
    if(fp!=0 && format1==1 && format2==0){


        //Find page size
        char * page_data;
        fseek(fp, 0, SEEK_END);
        long fpsize = ftell(fp);
        fseek(fp, 0, SEEK_SET);
        fprintf(stderr,"File Size is : %d\n", fpsize);

        //Read in html file
        page_data=malloc(fpsize);
        fread(page_data,sizeof(char),fpsize,fp);
		fclose(fp);

        //Get Date
        time_t current_time;
        char* c_time_string;
        current_time = time(NULL);
        c_time_string = ctime(&current_time);


        // Concatenate and send server response
        fprintf(stderr,"\nMessage is the same\n");
        sprintf(buffer,"HTTP/1.1 200 OK\r\nDate:%sContent Length: %d\r\nConnection: close\nContent Type:text/html\r\n\n",c_time_string,fpsize);
        send(*connfd, buffer, strlen(buffer),0);
        send(*connfd, page_data, strlen(page_data),0);
		free(page_data);
    }
    //Invalid requests
    else{
        strcpy(buffer,"\nHTTP/1.1 404 Not Found\n");
        send(*connfd, buffer, strlen(buffer),0);
        fprintf(stderr,"\nMessage is different\n");
    }

	//copy request to stat.txt
	pthread_mutex_lock(&mut);
	FILE *outputf;
    outputf=fopen("stat.txt","a");

	//get IP of client
	socklen_t len;
	struct sockaddr addr;
	len=sizeof(addr);
	getsockname(*connfd,&addr,&len);
	struct sockaddr_in *addr1=(struct sockaddr_in*)&addr; ;
	char *ip = inet_ntoa(addr1->sin_addr);
	int port  = ntohs(addr1->sin_port);

	fprintf(stderr,"Meassge before write: %s",input);


	//remove double new line in input
	for (i=0;i<buffersize;i++){
		if(input[i]==0){
			if(input[i-1]==10&&input[i-3]==10){
				input[i-1]=0;
				input[i-3]=0;
			}
			break;
		}

	}

    //Write request and Client IP and port to test.html
	fprintf(outputf,"%sClient: %s:%d\n\n",input,ip,port);
	fclose(outputf);
	pthread_mutex_unlock(&mut);



    //close sockets
    close(*connfd);

}

/////////////////////////////////////
//Main function, spawns worker thread
////////////////////////////////////

int main(){

    //create socket and struct
    int sfd;
    struct sockaddr_in addr;
    sfd= socket(PF_INET, SOCK_STREAM, 0);

    //Check for socket error
    if (sfd==-1){
        fprintf(stderr,"error 0\n");
    }


    addr.sin_family = AF_INET;
    addr.sin_port=htons(50020);
    addr.sin_addr.s_addr=INADDR_ANY;

    //bind and check for error
    if ( bind(sfd,(struct sockaddr *)&addr, sizeof(addr))==-1){
        fprintf(stderr,"error 1\n");
    }

    //Declare thread limit and array of threads
    int threadlimit=100;
    int connfdarray[threadlimit];
    int threadcounter=0;
    pthread_t threadarray[threadlimit];

    while(threadcounter<threadlimit){
        //Set up server port and check error
        if(listen(sfd,10)==-1){
            fprintf(stderr,"error 2\n");
        }

        //wait for connection and spawn thread when connection is made
        connfdarray[threadcounter]=accept(sfd, NULL, NULL);
         pthread_create(&threadarray[threadcounter], NULL, threadconnect, &connfdarray[threadcounter]);


        threadcounter++;
    }
    close(sfd);
    return 0;
}
