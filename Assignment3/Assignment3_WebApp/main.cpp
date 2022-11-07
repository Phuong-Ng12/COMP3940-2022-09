#include "Socket.h"
#include "ServerSocket.h"
#include <stddef.h>
#include <cstdio>

main() {
  ServerSocket *ss = new ServerSocket(8888);
	if (ss != NULL) {
		Socket *cs = ss->Accept();
		char *req = cs->getRequest();
		cs->sendResponse(req);
	}
}
