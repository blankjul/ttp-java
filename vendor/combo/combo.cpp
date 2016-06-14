extern "C"
{
#include "combo.h"
}
#include <istream>
#include <ostream>
#include <iostream>
#include <fstream>
#include <vector>
#include <algorithm> 
#include <string>


struct IDgreater
{
    bool operator()( const item& lx, const item& rx ) const {
    	return lx.id < rx.id;
    }
};


int main(int argc, char* argv[]) {

	std::istream *in;
	std::ifstream fin;
	std::ostream  *out;
	std::ofstream  fout;

	if (argc==2) {
	     fin.open(argv[1]);
	     in=&fin;

	     std::string file(argv[1]);
	     std::string postfix(".sol");
	     fout.open((file + postfix).c_str());
             out = &fout;
	} else {
	     in=&std::cin;
             out =&std::cout;
	}


     long maxWeight;
     (*in) >> maxWeight;

    long numOfItems;
    (*in) >> numOfItems;



    long p, w;
    std::vector<item> items;

    int counter = 0;
    while ((*in) >> p >> w)
    {
        item i;
        i.p = p;
        i.w = w;
        i.id = counter++;
        items.push_back(i);
    }

     if (items.size() != numOfItems) {
         std::cout << "ERROR while reading items. Read " << items.size() << " expected " << numOfItems<< "!\n";
         
         return EXIT_FAILURE;
     }

     item *f = &(*items.begin());
     item *l = &(*(items.end() - 1));

   
     long objValue = combo(f,l, maxWeight,0, 45654646464645, true, false);

     (*out) << "#" << objValue << "\n";

     std::sort( items.begin(), items.end(), IDgreater() );     

     for(std::vector<item>::iterator it = items.begin(); it != items.end(); ++it) {
          (*out) << "#" << it->id << " - " << it->p << " - " << it-> w << " - " << it->x << "\n";
          (*out) << it->x << "\n";
     }

	if (argc==2) {
		fin.close();
		fout.close();
	}


	return 0;

}


